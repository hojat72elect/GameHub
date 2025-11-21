
package com.paulrybitskyi.gamedge.common.domain.common.extensions

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

fun <T> T.asSuccess(): Result<T, Nothing> = Ok(this)

fun <T> T.asFailure(): Result<Nothing, T> = Err(this)

suspend fun <V, E> Result<V, E>.onSuccess(action: suspend (V) -> Unit): Result<V, E> {
    if (isOk) {
        action(value)
    }

    return this
}

suspend fun <V, E> Result<V, E>.onFailure(action: suspend (E) -> Unit): Result<V, E> {
    if (isErr) {
        action(error)
    }

    return this
}
