package ca.six.hojat.gamehub.common.data.common

import ca.six.hojat.gamehub.common.api.httpErrorMessage
import ca.six.hojat.gamehub.common.api.isHttpError
import ca.six.hojat.gamehub.common.api.isNetworkError
import ca.six.hojat.gamehub.common.api.isServerError
import ca.six.hojat.gamehub.common.api.isUnknownError
import ca.six.hojat.gamehub.common.api.networkErrorMessage
import ca.six.hojat.gamehub.common.api.unknownErrorMessage
import ca.six.hojat.gamehub.common.domain.common.entities.Error
import javax.inject.Inject
import ca.six.hojat.gamehub.common.api.Error as ApiError

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
