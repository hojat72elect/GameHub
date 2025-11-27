package ca.six.hojat.gamehub.core.urlopener

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object UrlOpenersModule {

    @Provides
    fun provideUrlOpeners(
        urlOpenersMap: Map<UrlOpenerKey.Type, @JvmSuppressWildcards UrlOpener>,
    ): List<UrlOpener> {
        return listOf(
            urlOpenersMap.getValue(UrlOpenerKey.Type.NATIVE_APP),
            urlOpenersMap.getValue(UrlOpenerKey.Type.CUSTOM_TAB),
            urlOpenersMap.getValue(UrlOpenerKey.Type.BROWSER),
        )
    }
}