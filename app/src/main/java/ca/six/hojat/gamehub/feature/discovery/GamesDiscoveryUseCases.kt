package ca.six.hojat.gamehub.feature.discovery

import ca.six.hojat.gamehub.common.domain.games.ObservableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.RefreshableGamesUseCase
import ca.six.hojat.gamehub.feature.discovery.di.GamesDiscoveryKey.Type
import javax.inject.Inject

class GamesDiscoveryUseCases @Inject constructor(
    private val observeGamesUseCasesMap: Map<Type, @JvmSuppressWildcards ObservableGamesUseCase>,
    private val refreshGamesUseCasesMap: Map<Type, @JvmSuppressWildcards RefreshableGamesUseCase>,
) {

    fun getObservableUseCase(keyType: Type): ObservableGamesUseCase {
        return observeGamesUseCasesMap.getValue(keyType)
    }

    fun getRefreshableUseCase(keyType: Type): RefreshableGamesUseCase {
        return refreshGamesUseCasesMap.getValue(keyType)
    }
}
