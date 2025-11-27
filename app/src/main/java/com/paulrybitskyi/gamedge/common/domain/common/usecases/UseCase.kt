package com.paulrybitskyi.gamedge.common.domain.common.usecases

interface UseCase<In, Out> {
    suspend fun execute(params: In): Out
}
