
package com.paulrybitskyi.gamedge.common.domain.common.usecases

import kotlinx.coroutines.flow.Flow

interface ObservableUseCase<In, Out> {
    fun execute(params: In): Flow<Out>
}
