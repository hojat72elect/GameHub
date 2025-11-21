package com.paulrybitskyi.gamedge.feature.category

import com.paulrybitskyi.gamedge.common.domain.games.ObservableGamesUseCase
import com.paulrybitskyi.gamedge.common.domain.games.RefreshableGamesUseCase
import com.paulrybitskyi.gamedge.feature.category.di.GamesCategoryKey.Type
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
