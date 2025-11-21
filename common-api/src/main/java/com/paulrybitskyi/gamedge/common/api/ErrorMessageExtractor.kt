package com.paulrybitskyi.gamedge.common.api

interface ErrorMessageExtractor {
    fun extract(responseBody: String): String
}
