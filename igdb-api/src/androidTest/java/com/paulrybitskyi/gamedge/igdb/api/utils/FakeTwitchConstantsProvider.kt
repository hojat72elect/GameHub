package com.paulrybitskyi.gamedge.igdb.api.utils

import com.paulrybitskyi.gamedge.igdb.api.common.TwitchConstantsProvider
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject

internal class FakeTwitchConstantsProvider @Inject constructor(
    mockWebServer: MockWebServer,
) : TwitchConstantsProvider {

    override val clientId: String = "client_id"
    override val clientSecret: String = "client_secret"
    override val apiBaseUrl: String = mockWebServer.url("/").toString()
}
