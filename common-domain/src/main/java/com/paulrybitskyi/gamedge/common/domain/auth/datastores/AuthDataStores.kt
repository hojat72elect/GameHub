package com.paulrybitskyi.gamedge.common.domain.auth.datastores

import javax.inject.Inject

class AuthDataStores @Inject constructor(
    val local: AuthLocalDataStore,
    val remote: AuthRemoteDataStore,
)
