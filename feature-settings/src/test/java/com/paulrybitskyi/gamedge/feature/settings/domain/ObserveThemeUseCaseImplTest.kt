package com.paulrybitskyi.gamedge.feature.settings.domain

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.common.extensions.execute
import com.paulrybitskyi.gamedge.common.testing.domain.MainCoroutineRule
import com.paulrybitskyi.gamedge.feature.settings.DOMAIN_SETTINGS
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveSettingsUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.usecases.ObserveThemeUseCaseImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class ObserveThemeUseCaseImplTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var observeSettingsUseCase: ObserveSettingsUseCase

    private lateinit var SUT: ObserveThemeUseCaseImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        SUT = ObserveThemeUseCaseImpl(
            observeSettingsUseCase = observeSettingsUseCase,
            dispatcherProvider = mainCoroutineRule.dispatcherProvider,
        )
    }

    @Test
    fun `Emits theme of settings that is emitted by another use case`() {
        runTest {
            every { observeSettingsUseCase.execute() } returns flowOf(DOMAIN_SETTINGS)

            SUT.execute().test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_SETTINGS.theme)
                awaitComplete()
            }
        }
    }

    @Test
    fun `Emits theme once if multiple events contain the same theme`() {
        runTest {
            every { observeSettingsUseCase.execute() } returns flowOf(DOMAIN_SETTINGS, DOMAIN_SETTINGS)

            SUT.execute().test {
                assertThat(awaitItem()).isEqualTo(DOMAIN_SETTINGS.theme)
                awaitComplete()
            }
        }
    }
}
