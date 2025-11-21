package com.paulrybitskyi.gamedge.feature.info.domain

import app.cash.turbine.test
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getError
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.common.entities.Error
import com.paulrybitskyi.gamedge.common.domain.games.datastores.GamesLocalDataStore
import com.paulrybitskyi.gamedge.common.testing.domain.DOMAIN_GAME
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.feature.info.GET_GAME_USE_CASE_PARAMS
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.GetGameUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class GetGameUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var gamesLocalDataStore: GamesLocalDataStore

    private lateinit var SUT: GetGameUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = GetGameUseCaseImpl(
            gamesLocalDataStore = gamesLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits game successfully`() {
        runTest {
            coEvery { gamesLocalDataStore.getGame(any()) } returns DOMAIN_GAME

            SUT.execute(GET_GAME_USE_CASE_PARAMS).test {
                assertThat(awaitItem().get()).isEqualTo(DOMAIN_GAME)
                awaitComplete()
            }
        }
    }

    @Test
    fun `Emits not found error if game ID does not reference existing game`() {
        runTest {
            coEvery { gamesLocalDataStore.getGame(any()) } returns null

            SUT.execute(GET_GAME_USE_CASE_PARAMS).test {
                assertThat(awaitItem().getError() is Error.NotFound).isTrue()
                awaitComplete()
            }
        }
    }
}
