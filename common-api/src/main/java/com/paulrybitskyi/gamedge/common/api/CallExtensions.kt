package com.paulrybitskyi.gamedge.common.api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal inline fun <T> Call<T>.enqueue(
    crossinline onResponse: (Call<T>, Response<T>) -> Unit = { _, _ -> },
    crossinline onFailure: (Call<T>, Throwable) -> Unit = { _, _ -> },
): Callback<T> {
    return object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = onResponse(call, response)
        override fun onFailure(call: Call<T>, throwable: Throwable) = onFailure(call, throwable)
    }
        .also(::enqueue)
}
