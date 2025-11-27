package ca.six.hojat.gamehub.feature.settings.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.extensions.execute
import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.feature.settings.domain.entities.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface ObserveThemeUseCase : ObservableUseCase<Unit, Theme>

@Singleton
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
