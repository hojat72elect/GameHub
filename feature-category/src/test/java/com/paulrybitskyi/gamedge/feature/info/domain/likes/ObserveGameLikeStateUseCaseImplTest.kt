
package com.paulrybitskyi.gamedge.feature.info.domain.likes

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.games.datastores.LikedGamesLocalDataStore
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.feature.info.OBSERVE_GAME_LIKE_STATE_USE_CASE_PARAMS
import com.paulrybitskyi.gamedge.feature.info.domain.usecases.likes.ObserveGameLikeStateUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ObserveGameLikeStateUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK private lateinit var likedGamesLocalDataStore: LikedGamesLocalDataStore

    private lateinit var SUT: ObserveGameLikeStateUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = ObserveGameLikeStateUseCaseImpl(
            likedGamesLocalDataStore = likedGamesLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits game like state successfully`() {
        runTest {
            every { likedGamesLocalDataStore.observeGameLikeState(any()) } returns flowOf(true)
            SUT.execute(OBSERVE_GAME_LIKE_STATE_USE_CASE_PARAMS).test {
                assertThat(awaitItem()).isTrue()
                awaitComplete()
            }

            every { likedGamesLocalDataStore.observeGameLikeState(any()) } returns flowOf(false)
            SUT.execute(OBSERVE_GAME_LIKE_STATE_USE_CASE_PARAMS).test {
                assertThat(awaitItem()).isFalse()
                awaitComplete()
            }
        }
    }
}
