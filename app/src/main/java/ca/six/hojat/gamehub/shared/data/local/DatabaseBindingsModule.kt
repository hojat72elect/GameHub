package ca.six.hojat.gamehub.shared.data.local

import ca.six.hojat.gamehub.shared.data.local.games.GamesTypeConverter
import ca.six.hojat.gamehub.shared.data.local.news_articles.ArticlesTypeConverter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface DatabaseBindingsModule {

    @Binds
    @IntoSet
    fun bindArticlesTypeConverter(binding: ArticlesTypeConverter): RoomTypeConverter

    @Binds
    @IntoSet
    fun bindGamesTypeConverter(binding: GamesTypeConverter): RoomTypeConverter
}
