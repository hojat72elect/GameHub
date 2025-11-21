package com.paulrybitskyi.gamedge.common.testing

import okhttp3.mockwebserver.MockWebServer

fun MockWebServer.startSafe() = try {
    start()
} catch (ignore: Throwable) {
    // ignore
}
