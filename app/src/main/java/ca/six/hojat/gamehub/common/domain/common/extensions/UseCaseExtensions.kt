package ca.six.hojat.gamehub.common.domain.common.extensions

import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.common.domain.common.usecases.UseCase
import kotlinx.coroutines.flow.Flow

suspend fun <Out> UseCase<Unit, Out>.execute(): Out {
    return execute(Unit)
}

fun <Out> ObservableUseCase<Unit, Out>.execute(): Flow<Out> {
    return execute(Unit)
}
