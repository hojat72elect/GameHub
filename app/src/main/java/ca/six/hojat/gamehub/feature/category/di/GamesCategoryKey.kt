package ca.six.hojat.gamehub.feature.category.di

import dagger.MapKey

@MapKey
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
internal annotation class GamesCategoryKey(val type: Type) {

    enum class Type {
        POPULAR,
        RECENTLY_RELEASED,
        COMING_SOON,
        MOST_ANTICIPATED,
    }
}
