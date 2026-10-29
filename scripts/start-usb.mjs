// Starts Metro for a USB-attached Android device (no LAN / Wi-Fi needed).
//
// Three things repeatedly break the dev-client connection on this setup:
//  1. Node resolves `localhost` to ::1 first, so Metro binds IPv6 only and
//     `adb reverse tcp:8081` (IPv4) has nothing to connect to.
//  2. The dev client remembers its last dev-server URL, which is the LAN IP the
//     phone cannot reach, so the launcher intent alone keeps failing.
//  3. An already running Metro makes Expo abort with EADDRINUSE, so a second
//     invocation looks like a failure even though the bundler is healthy.
//
// This script forces an IPv4 bind, sets up the reverse tunnel, reuses an
// already running Metro and prints the deep link that actually works.
//
// **Only run this script (through the script in `package.json`) if the normal run script is not working. **
// And also, only run the script from terminal rather than running it through WebStorm IDE.
//
import {spawn, spawnSync} from 'node:child_process';
import {existsSync} from 'node:fs';
import net from 'node:net';
import path from 'node:path';

const dnsOrder = '--dns-result-order=ipv4first';
process.env.NODE_OPTIONS = [process.env.NODE_OPTIONS, dnsOrder].filter(Boolean).join(' ');

const cliArgs = process.argv.slice(2);
const portFlagIndex = cliArgs.findIndex(arg => arg === '--port');
const cliPort = portFlagIndex === -1 ? undefined : cliArgs[portFlagIndex + 1];
const PORT = String(process.env.EXPO_PORT ?? cliPort ?? '8081');
const ALT_PORT = String(Number(PORT) + 1);
const DEEP_LINK = `exp+gamehub://?__expo_url=http%3A%2F%2F127.0.0.1%3A${PORT}`;

function findAdb() {
    const candidates = [
        process.env.ADB,
        process.env.ANDROID_HOME && path.join(process.env.ANDROID_HOME, 'platform-tools', 'adb.exe'),
        process.env.ANDROID_SDK_ROOT && path.join(process.env.ANDROID_SDK_ROOT, 'platform-tools', 'adb.exe'),
        process.env.LOCALAPPDATA && path.join(process.env.LOCALAPPDATA, 'Android', 'Sdk', 'platform-tools', 'adb.exe'),
        'adb',
    ].filter(Boolean);

    return candidates.find(candidate => candidate === 'adb' || existsSync(candidate)) ?? 'adb';
}

const adb = findAdb();

function runAdb(args) {
    const result = spawnSync(adb, args, {encoding: 'utf8'});
    if (result.error || result.status !== 0) {
        const detail = result.stderr?.trim() || result.error?.message || 'unknown error';
        console.warn(`[start-usb] adb ${args.join(' ')} failed: ${detail}`);
        return null;
    }
    return result.stdout.trim();
}

function isPortBusy(port) {
    return new Promise(resolve => {
        const socket = net.connect({host: '127.0.0.1', port: Number(port)});
        const settle = answer => {
            socket.destroy();
            resolve(answer);
        };
        socket.setTimeout(1500);
        socket.once('connect', () => settle(true));
        socket.once('timeout', () => settle(false));
        socket.once('error', () => settle(false));
    });
}

async function isMetroServing(port) {
    try {
        const response = await fetch(`http://127.0.0.1:${port}/status`, {signal: AbortSignal.timeout(2000)});
        return (await response.text()).includes('packager-status:running');
    } catch {
        return false;
    }
}

function printLaunchHint(indent = '   ') {
    console.log(`${indent}adb shell am start -a android.intent.action.VIEW -d "${DEEP_LINK}"`);
}

const devices = runAdb(['devices']);
const attached = devices
    ?.split(/\r?\n/)
    .slice(1)
    .map(line => line.trim())
    .filter(line => line.endsWith('\tdevice'));

if (!attached?.length) {
    console.warn('[start-usb] No device in "device" state — connect the phone over USB (and authorize debugging).');
} else if (runAdb(['reverse', `tcp:${PORT}`, `tcp:${PORT}`]) !== null) {
    console.log(`[start-usb] adb reverse tcp:${PORT} tcp:${PORT} is active`);
}

if (await isPortBusy(PORT)) {
    if (await isMetroServing(PORT)) {
        console.log(`[start-usb] Metro is already running on http://127.0.0.1:${PORT} — reusing it, not starting a second bundler.`);
        console.log('[start-usb] Reload the app with:');
        printLaunchHint();
        console.log(`[start-usb] If that Metro belongs to a different project, stop it or run: bun run start:usb --port ${ALT_PORT}`);
        process.exit(0);
    }

    console.error(`[start-usb] Port ${PORT} is taken by a process that is not Metro.`);
    console.error(`[start-usb] Inspect it with: Get-NetTCPConnection -LocalPort ${PORT} -State Listen`);
    console.error(`[start-usb] Then stop it, or use another port: bun run start:usb --port ${ALT_PORT}`);
    process.exit(1);
}

console.log(`[start-usb] Metro will advertise http://127.0.0.1:${PORT}`);
console.log('[start-usb] Launch the app with:');
printLaunchHint();

// bunx ships as an executable (bunx.exe on Windows), so no shell is needed;
// passing args together with `shell: true` triggers Node's DEP0190 warning.
const child = spawn('bunx', ['expo', 'start', '--dev-client', '--localhost', '--port', PORT], {
    stdio: 'inherit',
    env: process.env,
});

child.on('error', error => {
    console.error(`[start-usb] Could not launch bunx: ${error.message}`);
    console.error(`[start-usb] Start Metro manually with NODE_OPTIONS="${dnsOrder}"`);
    process.exit(1);
});

child.on('exit', code => process.exit(code ?? 0));
