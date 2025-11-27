package ca.six.hojat.gamehub.common.ui.di

import ca.six.hojat.gamehub.common.ui.images.CoilInitializer
import ca.six.hojat.gamehub.common.ui.images.ImageLoaderInitializer
import ca.six.hojat.gamehub.common.ui.widgets.games.GameUiModelMapper
import ca.six.hojat.gamehub.common.ui.widgets.games.GameUiModelMapperImpl
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
