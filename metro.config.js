const {getDefaultConfig} = require('expo/metro-config');

const config = getDefaultConfig(__dirname);

// SVG file support
config.resolver.assetExts.push('svg');

module.exports = config;
