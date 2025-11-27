package com.paulrybitskyi.gamedge.common.domain.common.entities

sealed class Error {

    sealed class ApiError : Error() {
        data class ClientError(val message: String) : ApiError()
        data class NetworkError(val message: String) : ApiError()
        data object ServiceUnavailable : ApiError()
        data class Unknown(val message: String) : ApiError()
    }

    data class NotFound(val message: String) : Error()
    data class Unknown(val message: String) : Error()
}
