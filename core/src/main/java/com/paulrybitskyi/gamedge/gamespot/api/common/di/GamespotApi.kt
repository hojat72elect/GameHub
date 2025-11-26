package com.paulrybitskyi.gamedge.gamespot.api.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FIELD,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.VALUE_PARAMETER,
)
internal annotation class GamespotApi
