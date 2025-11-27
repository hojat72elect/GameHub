package ca.six.hojat.gamehub.common.domain.games

import ca.six.hojat.gamehub.common.domain.common.DomainResult
import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.common.domain.games.common.ObserveGamesUseCaseParams
import ca.six.hojat.gamehub.common.domain.games.common.RefreshGamesUseCaseParams
import ca.six.hojat.gamehub.common.domain.games.entities.Game

typealias ObservableGamesUseCase = ObservableUseCase<ObserveGamesUseCaseParams, List<Game>>
typealias RefreshableGamesUseCase = ObservableUseCase<RefreshGamesUseCaseParams, DomainResult<List<Game>>>
