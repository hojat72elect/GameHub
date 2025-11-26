package com.paulrybitskyi.gamedge.igdb.api.common.errorextractors

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import com.paulrybitskyi.gamedge.igdb.api.common.di.qualifiers.IgdbApi
import javax.inject.Inject

private const val UNKNOWN_ERROR_TEMPLATE = "Unknown Error: %s"

@IgdbApi
internal class CompositeErrorMessageExtractor @Inject constructor(
    private val errorMessageExtractors: Set<@JvmSuppressWildcards ErrorMessageExtractor>,
) : ErrorMessageExtractor {

    override fun extract(responseBody: String): String {
        for (errorMessageExtractor in errorMessageExtractors) {
            try {
                val message = errorMessageExtractor.extract(responseBody)

                if (message.isNotBlank()) return message
            } catch (ignore: Throwable) {
                continue
            }
        }

        return String.format(
            UNKNOWN_ERROR_TEMPLATE,
            responseBody,
        )
    }
}
