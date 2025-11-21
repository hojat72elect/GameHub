
package com.paulrybitskyi.gamedge.common.api

sealed class Error {
    data class HttpError(val code: Int, val message: String) : Error()
    data class NetworkError(val throwable: Throwable) : Error()
    data class UnknownError(val throwable: Throwable) : Error()
}

val Error.isHttpError: Boolean
    get() = (this is Error.HttpError)

val Error.isServerError: Boolean
    get() = ((this is Error.HttpError) && (code >= HttpCodes.SERVER_ERROR))

val Error.isNetworkError: Boolean
    get() = (this is Error.NetworkError)

val Error.isUnknownError: Boolean
    get() = (this is Error.UnknownError)

val Error.httpErrorMessage: String
    get() = (if (this is Error.HttpError) message else "")

val Error.networkErrorMessage: String
    get() = (if (this is Error.NetworkError) (throwable.message ?: "") else "")

val Error.unknownErrorMessage: String
    get() = (if (this is Error.UnknownError) (throwable.message ?: "") else "")
