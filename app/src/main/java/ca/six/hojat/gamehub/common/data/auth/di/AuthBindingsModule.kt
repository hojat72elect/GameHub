package ca.six.hojat.gamehub.common.data.auth.di

import ca.six.hojat.gamehub.common.data.auth.datastores.database.AuthDatabaseDataStore
import ca.six.hojat.gamehub.common.data.auth.datastores.igdb.AuthIgdbDataStore
import ca.six.hojat.gamehub.common.data.auth.datastores.igdb.CredentialsStoreImpl
import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthLocalDataStore
import ca.six.hojat.gamehub.common.domain.auth.datastores.AuthRemoteDataStore
import ca.six.hojat.gamehub.igdb.api.common.CredentialsStore
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
    fun bindAuthLocalDataStore(binding: AuthDatabaseDataStore): AuthLocalDataStore

    @Binds
    @Singleton
    fun bindAuthRemoteDataStore(binding: AuthIgdbDataStore): AuthRemoteDataStore

    @Binds
    @Singleton
    fun bindCredentialsStore(binding: CredentialsStoreImpl): CredentialsStore
}
