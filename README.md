# GameHub 🎮

A modern mobile app for gaming discovery and news hub. GameHub helps users to discover new games, stay updated with latest news of gaming industry, and follow favorite titles.

## Main Features

- **Discovery**: Browse games across categories of `Popular`, `Recently Released`, `Coming Soon`, and `Most Anticipated`.
- **News**: Stay updated with the latest news about gaming and other related industries.
- **Likes**: Save and manage your favorite games.
- **Dark Mode Support**: Automatic theme switching based on system preferences.
- **Responsive Design**: Optimized for various screen sizes with safe area handling.

## Tech Stack

- **Framework**: React Native 0.81.5 with React 19.1.0
- **Platform**: Expo SDK ~54.0.33
- **Language**: TypeScript
- **Navigation**: expo-router (file-based routing) with React Navigation bottom tabs
- **HTTP Client**: Axios for API requests
- **UI Components**:
  - react-native-reanimated for animations
  - react-native-gesture-handler for gestures
  - react-native-safe-area-context for safe areas
  - react-native-svg for custom icons
- **Expo Modules**:
  - expo-haptics for haptic feedback
  - expo-image for optimized image handling
  - expo-file-system for file operations
  - expo-web-browser for in-app browsing
  - expo-splash-screen for splash screen
  - expo-updates for OTA updates
- **Package Manager**: Bun

## Getting Started

### Prerequisites

**Please pay attention tht at the time of writing, this app is only optimised for Android OS; if you are interested in porting this app to iOS, web, and/or desktop, fell free to fork the repo.**

- Node.js (recommended to use Bun as package manager)
- Expo CLI
- For Android: Android Studio with Android SDK

### Development

1. Install dependencies:

```bash
bun install
```

2. Start the development server:

```bash
bunx expo start
```

3. Run on your preferred platform:

- **Android**: Press `a` in the terminal
- **Web Browser**: Press `w` in the terminal

## Current Status

- ✅ Discover screen with game categories
- ✅ News screen with Gamespot integration (mocked data)
- ✅ Settings screen with theme options
- ⏳ Likes screen (placeholder - needs implementation)
- ⏳ Real API integration for news
- ⏳ Game details screen
- ⏳ Search functionality
- ⏳ User authentication
- ⏳ Persistent storage for likes

## Contributing

Contributions are welcome! Please feel free to submit a PR or open an issue about anything you like to see in this app.

## License

This project is private and proprietary.
