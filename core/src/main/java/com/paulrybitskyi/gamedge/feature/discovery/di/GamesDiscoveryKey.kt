package com.paulrybitskyi.gamedge.feature.discovery.di

import dagger.MapKey

@MapKey
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class GamesDiscoveryKey(val type: Type) {

    enum class Type {
        POPULAR,
        RECENTLY_RELEASED,
        COMING_SOON,
        MOST_ANTICIPATED,
    }
}
