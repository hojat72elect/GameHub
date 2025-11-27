package ca.six.hojat.gamehub.igdb.api.games

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface GamesBindingsModule {

    @Binds
    @Singleton
    fun bindGamesEndpoint(binding: GamesEndpointImpl): GamesEndpoint

    @Binds
    fun bindIgdbApiQueryFactory(binding: IgdbApiQueryFactoryImpl): IgdbApiQueryFactory
}
