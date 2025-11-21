
@file:Suppress("MagicNumber")

package com.paulrybitskyi.gamedge.common.testing

import com.paulrybitskyi.gamedge.common.api.Error as ApiError

val API_ERROR_HTTP = ApiError.HttpError(code = 10, message = "message")
val API_ERROR_NETWORK = ApiError.NetworkError(Exception("message"))
val API_ERROR_UNKNOWN = ApiError.UnknownError(Exception("message"))
