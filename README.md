# GameHub

![Min API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

An Android application for browsing video games and checking the latest gaming news from around the
world.

Built entirely using Kotlin technologies.

## Contents

* [Demonstration](#demonstration)
    * [Video](#video)
    * [Screenshots](#screenshots)
* [Tech Stack](#tech-stack)
* [Architecture](#architecture)
* [Development Setup](#development-setup)
    * [IGDB](#igdb)
    * [Gamespot](#gamespot)
* [License](#license)
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

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android
  development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for
  building native UI.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
  and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) -
  Official Kotlin's tooling for performing asynchronous work.
- [MVVM/MVI Architecture](https://developer.android.com/jetpack/guide) - Official recommended
  architecture for building robust, production-quality apps.
- [Android Jetpack](https://developer.android.com/jetpack) - Jetpack is a suite of libraries to help
  developers build state-of-the-art applications.
    - [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) - Navigation
      Compose is a framework for navigating between composables while taking advantage of the
      Navigation component’s infrastructure and features.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - The
      ViewModel is designed to store and manage UI-related data in a lifecycle conscious way.
    - [StateFlow](https://developer.android.com/kotlin/flow/stateflow-and-sharedflow#stateflow) -
      StateFlow is a state-holder observable flow that emits the current and new state updates to
      its collectors.
    - [Room](https://developer.android.com/topic/libraries/architecture/room) - The Room library
      provides an abstraction layer over SQLite to allow for more robust database access.
    - [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) - DataStore
      is a data storage solution that stores key-value pairs or typed objects
      with [protocol buffers](https://developers.google.com/protocol-buffers).
    - [Dagger Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - Hilt
      is a dependency injection library for Android.
    - [MotionLayout](https://developer.android.com/training/constraint-layout/motionlayout) -
      MotionLayout allows you to create beautiful animations in your app without too much hassle.
    - [Custom Tabs](https://developers.google.com/web/android/custom-tabs/implementation-guide) -
      Custom Tabs is a browser feature that gives apps more control over their web experience.
- [Accompanist](https://github.com/google/accompanist) - A collection of extension libraries for
  Jetpack Compose.
- [OkHttp](https://github.com/square/okhttp) - An HTTP client for making network calls.
- [Retrofit](https://github.com/square/retrofit) - A library for building REST API clients.
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) - A multiplatform Kotlin
  serialization library.
- [Coil](https://github.com/coil-kt/coil) - An image loading library.
- [Kotlin Result](https://github.com/michaelbull/kotlin-result) - A multiplatform Result monad for
  modelling success or failure operations.
- [Gradle's Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Gradle’s Kotlin
  DSL is an alternative syntax to the Groovy DSL with an enhanced editing experience.

For more information about used dependencies, see [this](./gradle/libs.versions.toml)
file.

## Architecture

![architecture](/media/architecture.png)

## Development Setup

You'll need to supply API/client keys for the various services that the app uses in order to build
the application.

### IGDB

[IGDB](https://www.igdb.com/discover) is a website dedicated to combining all the relevant
information about games into a comprehensive resource for gamers everywhere. This is the main API
that the app uses to fetch information about pretty much any video game there is.

Check [this link](https://api-docs.igdb.com/#account-creation) on how to obtain a client ID and
secret. Once you have obtained the keys, you can set them in your `~/.gradle/gradle.properties`:

```
TWITCH_APP_CLIENT_ID=yout_client_id_here
TWITCH_APP_CLIENT_SECRET=your_client_secret_here
```

### Gamespot

[Gamespot](https://www.gamespot.com/) is a video gaming website that provides news, reviews,
previews, downloads, and other information on video games. The app uses its API to solely retrieve
the latest news in the gaming world.

Check [this link](https://www.gamespot.com/api/) on how to obtain an API key. Once you have obtained
the key, you can set it in your `~/.gradle/gradle.properties`:

```
GAMESPOT_API_KEY=your_api_key_here
```

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

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
