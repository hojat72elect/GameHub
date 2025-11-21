
package com.paulrybitskyi.gamedge.common.data.auth.datastores.file

import androidx.datastore.core.DataStore
import com.paulrybitskyi.gamedge.common.domain.auth.datastores.AuthLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.auth.entities.OauthCredentials
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BindType
internal class AuthFileDataStore @Inject constructor(
    private val protoDataStore: DataStore<ProtoOauthCredentials>,
    private val protoAuthMapper: ProtoAuthMapper,
) : AuthLocalDataStore {

    override suspend fun saveOauthCredentials(oauthCredentials: OauthCredentials) {
        protoDataStore.updateData {
            protoAuthMapper.mapToProtoOauthCredentials(oauthCredentials)
        }
    }

    override suspend fun getOauthCredentials(): OauthCredentials? {
        // Using Kotlin's takeIf and let because Flow's filter with
        // first/firstOrNull does not seem to be working.
        // When Flow's chain is fixed, consider offloading
        // mapping and filtering operations to a background thread
        // since they currently run on the main thread.

        return protoDataStore.data
            .firstOrNull()
            ?.takeIf(ProtoOauthCredentials::isNotEmpty)
            ?.let(protoAuthMapper::mapToDomainOauthCredentials)
    }
}
