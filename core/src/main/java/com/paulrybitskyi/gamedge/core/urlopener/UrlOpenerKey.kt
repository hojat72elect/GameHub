package com.paulrybitskyi.gamedge.core.urlopener

import dagger.MapKey

@MapKey
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class UrlOpenerKey(val type: Type) {

    enum class Type {
        NATIVE_APP,
        CUSTOM_TAB,
        BROWSER,
    }
}
