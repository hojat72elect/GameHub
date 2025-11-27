package ca.six.hojat.gamehub.common.data.auth.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import ca.six.hojat.gamehub.common.data.auth.datastores.file.ProtoOauthCredentials
import ca.six.hojat.gamehub.common.data.auth.datastores.file.ProtoOauthCredentialsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

private const val AUTH_PREFERENCES_DATA_STORE_NAME = "auth.pb"

private val Context.authProtoDataStore by dataStore(
    fileName = AUTH_PREFERENCES_DATA_STORE_NAME,
    serializer = ProtoOauthCredentialsSerializer,
)

@Module
@InstallIn(SingletonComponent::class)
internal object CoreModule {

    @Provides
    fun provideAuthProtoDataStore(
        @ApplicationContext context: Context,
    ): DataStore<ProtoOauthCredentials> {
        return context.authProtoDataStore
    }
}
