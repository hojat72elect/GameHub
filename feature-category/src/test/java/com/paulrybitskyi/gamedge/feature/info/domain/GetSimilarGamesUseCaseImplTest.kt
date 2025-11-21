
package com.paulrybitskyi.gamedge.feature.info.domain

import app.cash.turbine.test
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.get
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAMES
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.feature.info.GET_SIMILAR_GAMES_USE_CASE_PARAMS
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetSimilarGamesUseCaseImpl
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.RefreshSimilarGamesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class GetSimilarGamesUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK private lateinit var refreshSimilarGamesUseCase: RefreshSimilarGamesUseCase
    @MockK private lateinit var gamesLocalDataStore: GamesLocalDataStore

    private lateinit var SUT: GetSimilarGamesUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = GetSimilarGamesUseCaseImpl(
            refreshSimilarGamesUseCase = refreshSimilarGamesUseCase,
            gamesLocalDataStore = gamesLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits games that refresh use case successfully emits`() {
        runTest {
            coEvery { refreshSimilarGamesUseCase.execute(any()) } returns flowOf(Ok(DOMAIN_GAMES))

            SUT.execute(GET_SIMILAR_GAMES_USE_CASE_PARAMS).test {
                assertThat(awaitItem().get()).isEqualTo(DOMAIN_GAMES)
                awaitComplete()
            }
        }
    }

    @Test
    fun `Emits games from local data store if refresh use case does not emit`() {
        runTest {
            coEvery { refreshSimilarGamesUseCase.execute(any()) } returns flowOf()
            coEvery { gamesLocalDataStore.getSimilarGames(any(), any()) } returns DOMAIN_GAMES

            SUT.execute(GET_SIMILAR_GAMES_USE_CASE_PARAMS).test {
                assertThat(awaitItem().get()).isEqualTo(DOMAIN_GAMES)
                awaitComplete()
            }
        }
    }
}
