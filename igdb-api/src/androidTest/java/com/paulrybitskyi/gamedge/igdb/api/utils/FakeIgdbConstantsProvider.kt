package com.paulrybitskyi.gamedge.igdb.api.utils

import com.paulrybitskyi.gamedge.igdb.api.common.IgdbConstantsProvider
import okhttp3.mockwebserver.MockWebServer
import javax.inject.Inject

internal class FakeIgdbConstantsProvider @Inject constructor(
    mockWebServer: MockWebServer,
) : IgdbConstantsProvider {

    override val apiBaseUrl: String = mockWebServer.url("/").toString()
}
