
package com.paulrybitskyi.gamedge.common.api.calladapter

import com.paulrybitskyi.gamedge.common.api.ErrorMessageExtractor
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import com.github.michaelbull.result.Result as ApiResult
import com.paulrybitskyi.gamedge.common.api.Error as ApiError

private const val PARAM_UPPER_BOUND_INDEX_API_RESULT = 0
private const val PARAM_UPPER_BOUND_INDEX_SUCCESS = 0
private const val PARAM_UPPER_BOUND_INDEX_ERROR = 1

class ApiResultCallAdapterFactory(
    private val errorMessageExtractor: ErrorMessageExtractor,
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit,
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) return null
        check(returnType is ParameterizedType) { "Return type must be a parameterized type." }

        val apiResultType = getParameterUpperBound(PARAM_UPPER_BOUND_INDEX_API_RESULT, returnType)
        if (getRawType(apiResultType) != ApiResult::class.java) return null
        check(apiResultType is ParameterizedType) { "Response type must be a parameterized type." }

        val errorType = getParameterUpperBound(PARAM_UPPER_BOUND_INDEX_ERROR, apiResultType)
        if (getRawType(errorType) != ApiError::class.java) return null

        val successType = getParameterUpperBound(PARAM_UPPER_BOUND_INDEX_SUCCESS, apiResultType)
        return ApiResultCallAdapter<Any>(successType, errorMessageExtractor)
    }
}
