package com.paulrybitskyi.gamedge.common.data.auth.di

import com.paulrybitskyi.gamedge.common.data.auth.datastores.file.AuthFileDataStore
import com.paulrybitskyi.gamedge.common.data.auth.datastores.igdb.AuthIgdbDataStore
import com.paulrybitskyi.gamedge.common.data.auth.datastores.igdb.CredentialsStoreImpl
import com.paulrybitskyi.gamedge.common.domain.auth.datastores.AuthLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.auth.datastores.AuthRemoteDataStore
import com.paulrybitskyi.gamedge.igdb.api.common.CredentialsStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface AuthBindingsModule {

    @Binds
    @Singleton
    fun bindAuthLocalDataStore(binding: AuthFileDataStore): AuthLocalDataStore

    @Binds
    @Singleton
    fun bindAuthRemoteDataStore(binding: AuthIgdbDataStore): AuthRemoteDataStore

    @Binds
    @Singleton
    fun bindCredentialsStore(binding: CredentialsStoreImpl): CredentialsStore
}
