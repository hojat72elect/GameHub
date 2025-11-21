
package com.paulrybitskyi.gamedge.core.urlopener

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UrlOpenersModule {

    @Provides
    fun provideUrlOpeners(
        @UrlOpenerKey(UrlOpenerKey.Type.NATIVE_APP) nativeAppUrlOpener: UrlOpener,
        @UrlOpenerKey(UrlOpenerKey.Type.CUSTOM_TAB) customTabUrlOpener: UrlOpener,
        @UrlOpenerKey(UrlOpenerKey.Type.BROWSER) browserUrlOpener: UrlOpener,
    ): List<UrlOpener> {
        return listOf(nativeAppUrlOpener, customTabUrlOpener, browserUrlOpener)
    }
}
