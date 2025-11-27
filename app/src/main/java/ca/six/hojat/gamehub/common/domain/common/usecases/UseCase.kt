package ca.six.hojat.gamehub.common.domain.common.usecases

interface UseCase<In, Out> {
    suspend fun execute(params: In): Out
}
