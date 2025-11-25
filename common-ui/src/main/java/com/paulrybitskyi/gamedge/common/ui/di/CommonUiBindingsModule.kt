package com.paulrybitskyi.gamedge.common.ui.di

import com.paulrybitskyi.gamedge.common.ui.images.CoilInitializer
import com.paulrybitskyi.gamedge.common.ui.images.ImageLoaderInitializer
import com.paulrybitskyi.gamedge.common.ui.widgets.games.GameUiModelMapper
import com.paulrybitskyi.gamedge.common.ui.widgets.games.GameUiModelMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonUiBindingsModule {

    @Binds
    fun bindImageLoaderInitializer(binding: CoilInitializer): ImageLoaderInitializer
}

@Module
@InstallIn(ViewModelComponent::class)
internal interface CommonUiViewModelBindingsModule {

    @Binds
    fun bindGameUiModelMapper(binding: GameUiModelMapperImpl): GameUiModelMapper
}
