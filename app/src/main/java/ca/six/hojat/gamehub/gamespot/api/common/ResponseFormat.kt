package ca.six.hojat.gamehub.gamespot.api.common

internal enum class ResponseFormat(val rawFormat: String) {
    XML("xml"),
    JSON("json"),
    JSONP("jsonp"),
}
