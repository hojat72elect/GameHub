package com.paulrybitskyi.gamedge.common.domain.games

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.games.common.ObserveGamesUseCaseParams
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.domain.games.usecases.ObserveMostAnticipatedGamesUseCaseImpl
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAMES
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ObserveMostAnticipatedGamesUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var gamesLocalDataStore: GamesLocalDataStore

    private lateinit var SUT: ObserveMostAnticipatedGamesUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = ObserveMostAnticipatedGamesUseCaseImpl(
            gamesLocalDataStore = gamesLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits games successfully`() {
        runTest {
            every { gamesLocalDataStore.observeMostAnticipatedGames(any()) } returns flowOf(DOMAIN_GAMES)

            SUT.execute(ObserveGamesUseCaseParams()).test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_GAMES)
                awaitComplete()
            }
        }
    }
}
