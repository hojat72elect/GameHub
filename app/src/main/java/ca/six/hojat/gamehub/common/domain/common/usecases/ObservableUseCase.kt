package ca.six.hojat.gamehub.common.domain.common.usecases

import kotlinx.coroutines.flow.Flow

interface ObservableUseCase<In, Out> {
    fun execute(params: In): Flow<Out>
}
