package com.paulrybitskyi.gamedge.database.common.di

import com.paulrybitskyi.gamedge.database.articles.ArticlesTypeConverter
import com.paulrybitskyi.gamedge.database.common.RoomTypeConverter
import com.paulrybitskyi.gamedge.database.games.GamesTypeConverter
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
