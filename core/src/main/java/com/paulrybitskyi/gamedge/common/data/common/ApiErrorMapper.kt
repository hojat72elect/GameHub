package com.paulrybitskyi.gamedge.common.data.common

import com.paulrybitskyi.gamedge.common.api.httpErrorMessage
import com.paulrybitskyi.gamedge.common.api.isHttpError
import com.paulrybitskyi.gamedge.common.api.isNetworkError
import com.paulrybitskyi.gamedge.common.api.isServerError
import com.paulrybitskyi.gamedge.common.api.isUnknownError
import com.paulrybitskyi.gamedge.common.api.networkErrorMessage
import com.paulrybitskyi.gamedge.common.api.unknownErrorMessage
import com.paulrybitskyi.gamedge.common.domain.common.entities.Error
import javax.inject.Inject
import com.paulrybitskyi.gamedge.common.api.Error as ApiError

class ApiErrorMapper @Inject constructor() {

    fun mapToDomainError(apiError: ApiError): Error = with(apiError) {
        return when {
            isServerError -> Error.ApiError.ServiceUnavailable
            isHttpError -> Error.ApiError.ClientError(httpErrorMessage)
            isNetworkError -> Error.ApiError.NetworkError(networkErrorMessage)
            isUnknownError -> Error.ApiError.Unknown(unknownErrorMessage)

            else -> error("Could not map the api error $this to a data error.")
        }
    }
}
