
package com.paulrybitskyi.gamedge.feature.settings.domain.usecases

import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.extensions.execute
import com.paulrybitskyi.gamedge.common.domain.common.usecases.ObservableUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.entities.Theme
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ObserveThemeUseCase : ObservableUseCase<Unit, Theme>

@Singleton
@BindType
internal class ObserveThemeUseCaseImpl @Inject constructor(
    private val observeSettingsUseCase: ObserveSettingsUseCase,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveThemeUseCase {

    override fun execute(params: Unit): Flow<Theme> {
        return observeSettingsUseCase.execute()
            .map { settings -> settings.theme }
            .distinctUntilChanged()
            .flowOn(dispatcherProvider.main)
    }
}
