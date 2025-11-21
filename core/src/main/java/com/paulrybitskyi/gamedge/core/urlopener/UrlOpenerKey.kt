package com.paulrybitskyi.gamedge.core.urlopener

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class UrlOpenerKey(val type: Type) {

    enum class Type {
        NATIVE_APP,
        CUSTOM_TAB,
        BROWSER,
    }
}
