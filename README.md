# `GameHub` Android app


[![Build](https://github.com/hojat72elect/GameHub/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/hojat72elect/GameHub/actions/workflows/build.yml)

This is an Android client for <a href="https://www.igdb.com/">IGDB</a>
and <a href="https://dev.twitch.tv/">Twitch</a> APIs for all the latest news and information about
video games.

![Min API](https://img.shields.io/badge/API-21%2B-orange.svg?style=flat)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)

App's UI and presentation layer are built entirely using the Jetpack Compose.

## Contents

* [Demonstration](#demonstration)
    * [Videos](#videos)
    * [Screenshots](#screenshots)
* [Tech Stack](#tech-stack)
* [Architecture](#architecture)
* [Development Setup](#development-setup)
    * [IGDB](#igdb)
    * [Gamespot](#gamespot)
* [Contributors](#Contributors)
* [Questions](#questions)

## Demonstration

### Videos

<details>
<summary><b>Demo 1</b></summary>


https://user-images.githubusercontent.com/14782808/111520186-88671800-8760-11eb-8995-8e45a5cd9213.mp4
</details>
<details>
<summary><b>Demo 2</b></summary>


https://user-images.githubusercontent.com/14782808/111520260-9b79e800-8760-11eb-9665-1062ed2b2c24.mp4
</details>
<details>
<summary><b>Demo 3</b></summary>


https://user-images.githubusercontent.com/14782808/111520365-b187a880-8760-11eb-9dbe-0ffc44635ef8.mp4
</details>

### Screenshots

<p>
<img src="/media/screenshot1.png" width="32%" alt="An screen shot of the app"/>
<img src="/media/screenshot2.png" width="32%" alt="An screen shot of the app"/>
<img src="/media/screenshot3.png" width="32%" alt="An screen shot of the app"/>
</p> 
<p> 
<img src="/media/screenshot4.png" width="32%" alt="An screen shot of the app"/>
<img src="/media/screenshot5.png" width="32%" alt="An screen shot of the app"/>
<img src="/media/screenshot6.png" width="32%" alt="An screen shot of the app"/>
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
- [Lottie](http://airbnb.io/lottie/#/README) - A multiplatform UI library for parsing Adobe After
  Effects animations exported as JSON.
- [OkHttp](https://github.com/square/okhttp) - An HTTP client for making network calls.
- [Retrofit](https://github.com/square/retrofit) - A library for building REST API clients.
- [KotlinX Serialization](https://github.com/Kotlin/kotlinx.serialization) - A multiplatform Kotlin
  serialization library.
- [Coil](https://github.com/coil-kt/coil) - An image loading library.
- [Hilt Binder](https://github.com/mars885/hilt-binder) - An annotating processing library that
  automatically generates Dagger Hilt's `@Binds` methods.
- [Kotlin Result](https://github.com/michaelbull/kotlin-result) - A multiplatform Result monad for
  modelling success or failure operations.
- [Napier](https://github.com/AAkira/Napier) - Kotlin Multiplatform logging library.
- [Gradle's Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) - Gradle’s Kotlin
  DSL is an alternative syntax to the Groovy DSL with an enhanced editing experience.
- [buildSrc](https://docs.gradle.org/current/userguide/organizing_gradle_projects.html#sec:build_sources)
    - A special
      module within the project to manage dependencies and whatnot.

For more information about used dependencies, see [this](/buildSrc/src/main/java/Dependencies.kt)
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

### Gamespot

[Gamespot](https://www.gamespot.com/) is a video gaming website that provides news, reviews,
previews, downloads, and other information on video games. The app uses its API to solely retrieve
the latest news in the gaming world.

### Contributors

Main developer: [Hojat Ghasemi](mailto:hojat72elect@gmail.com)
<br/>
App icon designer: [Grace Peterson](mailto:gracepeterson2@outlook.com)