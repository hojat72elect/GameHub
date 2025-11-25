package com.paulrybitskyi.gamedge.igdb.api.common.di

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.IgdbAuthenticator
import com.paulrybitskyi.gamedge.igdb.api.common.di.qualifiers.ErrorMessageExtractorKey
import com.paulrybitskyi.gamedge.igdb.api.common.di.qualifiers.IgdbApi
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.CompositeErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.IgdbErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.errorextractors.TwitchErrorMessageExtractor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator

@Module
@InstallIn(SingletonComponent::class)
internal interface CommonBindingsModule {

    @Binds
    @ErrorMessageExtractorKey(ErrorMessageExtractorKey.Type.IGDB)
    fun bindIgdbErrorMessageExtractor(binding: IgdbErrorMessageExtractor): ErrorMessageExtractor

    @Binds
    @ErrorMessageExtractorKey(ErrorMessageExtractorKey.Type.TWITCH)
    fun bindTwitchErrorMessageExtractor(binding: TwitchErrorMessageExtractor): ErrorMessageExtractor

    @Binds
    @IgdbApi
    fun bindCompositeErrorMessageExtractor(binding: CompositeErrorMessageExtractor): ErrorMessageExtractor

    @Binds
    fun bindIgdbAuthenticator(binding: IgdbAuthenticator): Authenticator
}
