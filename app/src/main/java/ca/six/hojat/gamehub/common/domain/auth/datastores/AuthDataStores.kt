package ca.six.hojat.gamehub.common.domain.auth.datastores

import javax.inject.Inject

class AuthDataStores @Inject constructor(
    val local: AuthLocalDataStore,
    val remote: AuthRemoteDataStore,
)
