package ca.six.hojat.gamehub.common.extensions

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri


/**
 * Checks if the given url can be opened by a native application on the device.
 *
 * Note: Due to [Android 11 package visibility changes](https://g.co/dev/packagevisibility), this
 * method does not work on Android 11 and above.
 *
 * @param url The url to check
 *
 * @return true if the url can be opened by a native app; false otherwise
 */
fun PackageManager.canUrlBeOpenedByNativeApp(url: String): Boolean {
    return (getNativeAppPackageForUrl(url) != null)
}


/**
 * Tries to figure out a package name of a native application that
 * is able to open the given url.
 *
 * Note: Due to [Android 11 package visibility changes](https://g.co/dev/packagevisibility), this
 * method does not work on Android 11 and above.
 *
 * @param url The given url
 *
 * @return The package name of a native application or null.
 */
@SuppressLint("QueryPermissionsNeeded")
fun PackageManager.getNativeAppPackageForUrl(url: String): String? {
    val genericAppsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.example.com"))
    val resolvedGenericApps = queryIntentActivities(genericAppsIntent, 0).map {
        it.activityInfo.packageName
    }

    val specializedAppsIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val resolvedSpecializedApps = queryIntentActivities(specializedAppsIntent, 0).map {
        it.activityInfo.packageName
    }

    return resolvedSpecializedApps
        .subtract(resolvedGenericApps)
        .firstOrNull()
}