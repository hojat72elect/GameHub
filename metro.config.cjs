const {getDefaultConfig} = require('expo/metro-config');

// eslint-disable-next-line no-undef
const config = getDefaultConfig(__dirname);

// Remove SVG from asset extensions because it will be treated as a React Component
config.resolver.assetExts = config.resolver.assetExts.filter(ext => ext !== "svg");

// Add SVG to source extensions so we can import it easily (just like a React component)
config.resolver.sourceExts.push("svg");

// Use our 3rd party transformer for handling SVG files
config.transformer.babelTransformerPath = require.resolve("react-native-svg-transformer");

module.exports = config;
