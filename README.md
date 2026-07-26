# GameHub 🎮

[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)

A multiplatform mobile app for browsing video games and checking the latest gaming news from around the world.
Built entirely using TypeScript technologies.

## Contents

* [Demonstration](#demonstration)
    * [Video](#video)
    * [Screenshots](#screenshots)
* [Tech Stack](#tech-stack)
* [Development Setup](#development-setup)
    * [IGDB](#igdb)
    * [Gamespot](#gamespot)
* [Contributing](#contributing)

## Demonstration

### Video

https://github.com/user-attachments/assets/a8cde6c6-1e89-4986-a5d7-3dc98cb014c5

### Screenshots

<p>
<img src="/media/screenshot1.png" width="32%"/>
<img src="/media/screenshot2.png" width="32%"/>
<img src="/media/screenshot3.png" width="32%"/>
</p>
<p>
<img src="/media/screenshot4.png" width="32%"/>
<img src="/media/screenshot5.png" width="32%"/>
<img src="/media/screenshot6.png" width="32%"/>
</p>

## Tech Stack

- [React Native](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [React](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
  building native UI.
- [Expo SDK](https://kotlinlang.org/) - First class and official programming language for Android
    development.
- [expo-router](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.
- [Axios ](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [react-native-reanimated](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
  building native UI.
- [react-native-gesture-handler](https://kotlinlang.org/) - First class and official programming language for Android
    development.
- [react-native-safe-area-context](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.
- [react-native-svg](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.
- [expo-haptics](https://kotlinlang.org/) - First class and official programming language for Android
    development.
- [expo-image](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.
- [expo-file-system](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [expo-web-browser](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
  building native UI.
- [expo-splash-screen](https://kotlinlang.org/) - First class and official programming language for Android
    development.
- [expo-updates](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.
- [Bun](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
    building native UI.

## Development Setup

You'll need to supply API/client keys for the various services that the app uses in order to build
the application.

### IGDB

[IGDB](https://www.igdb.com/discover) is a website dedicated to combining all the relevant
information about games into a comprehensive resource for gamers everywhere. This is the main API
that the app uses to fetch information about pretty much any video game there is.

Check [this link](https://api-docs.igdb.com/#account-creation) on how to obtain a client ID and
secret. Once you have obtained the keys, you can set them in your `.env`:

```
TWITCH_APP_CLIENT_ID=your_client_id_here
TWITCH_APP_CLIENT_SECRET=your_client_secret_here
```

### Gamespot

[Gamespot](https://www.gamespot.com/) is a video gaming website that provides news, reviews,
previews, downloads, and other information on video games. The app uses its API to solely retrieve
the latest news in the gaming world.

Check [this link](https://www.gamespot.com/api/) on how to obtain an API key. Once you have obtained
the key, you can set it in your `.env`:

```
GAMESPOT_API_KEY=your_api_key_here
```


## Contributing

❤️ Contributions are always welcome! ❤️

If you'd like to help, please have a look at
the [open issues](https://github.com/hojat72elect/GameHub/issues).
Currently, we are particularly interested in help with migrating the application to Kotlin
Multiplatform (KMP).

Please feel free to open a pull request or an issue if you have any suggestions or improvements.


<p align="center">
  <a href="https://buymeacoffee.com/hojat" target="_blank">
    <img src="https://media4.giphy.com/media/v1.Y2lkPTc5MGI3NjExN2tqa3dkMzFjOHFub25kZmhwbXZ3aG5jZzU0Nnc4cjhzenI5b21zbyZlcD12MV9pbnRlcm5hbF9naWZfYnlfaWQmY3Q9cw/o7RZbs4KAA6tvM4H6j/giphy.gif" alt="buy me a cup of tea"/>
  </a>
</p>
