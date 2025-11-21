
package com.paulrybitskyi.gamedge.feature.settings.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.common.extensions.execute
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.feature.settings.DOMAIN_SETTINGS
import com.paulrybitskyi.gamedge.feature.settings.domain.datastores.SettingsLocalDataStore
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveSettingsUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ObserveSettingsUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK private lateinit var settingsLocalDataStore: SettingsLocalDataStore

    private lateinit var SUT: ObserveSettingsUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = ObserveSettingsUseCaseImpl(
            localDataStore = settingsLocalDataStore,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits settings from local data store`() {
        runTest {
            every { settingsLocalDataStore.observeSettings() } returns flowOf(DOMAIN_SETTINGS)

            SUT.execute().test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_SETTINGS)
                awaitComplete()
            }
        }
    }
}
