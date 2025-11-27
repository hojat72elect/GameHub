package ca.six.hojat.gamehub.common.api

interface ErrorMessageExtractor {
    fun extract(responseBody: String): String
}
