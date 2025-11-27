package ca.six.hojat.gamehub.feature.category

import ca.six.hojat.gamehub.common.domain.games.ObservableGamesUseCase
import ca.six.hojat.gamehub.common.domain.games.RefreshableGamesUseCase
import ca.six.hojat.gamehub.feature.category.di.GamesCategoryKey.Type
import javax.inject.Inject
import javax.inject.Provider

internal class GamesCategoryUseCases @Inject constructor(
    private val observeGamesUseCasesMap: Map<Type, @JvmSuppressWildcards Provider<ObservableGamesUseCase>>,
    private val refreshGamesUseCasesMap: Map<Type, @JvmSuppressWildcards Provider<RefreshableGamesUseCase>>,
) {

    fun getObservableUseCase(keyType: Type): ObservableGamesUseCase {
        return observeGamesUseCasesMap.getValue(keyType).get()
    }

    fun getRefreshableUseCase(keyType: Type): RefreshableGamesUseCase {
        return refreshGamesUseCasesMap.getValue(keyType).get()
    }
}
