package ca.six.hojat.gamehub.gamespot.api.articles

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ArticlesBindingsModule {

    @Binds
    @Singleton
    fun bindArticlesEndpoint(binding: ArticlesEndpointImpl): ArticlesEndpoint
}
