package ca.six.hojat.gamehub.feature.settings.domain.usecases

import ca.six.hojat.gamehub.common.domain.common.DispatcherProvider
import ca.six.hojat.gamehub.common.domain.common.usecases.ObservableUseCase
import ca.six.hojat.gamehub.feature.settings.domain.datastores.SettingsLocalDataStore
import ca.six.hojat.gamehub.feature.settings.domain.entities.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal interface ObserveSettingsUseCase : ObservableUseCase<Unit, Settings>

internal class ObserveSettingsUseCaseImpl @Inject constructor(
    private val localDataStore: SettingsLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveSettingsUseCase {

    override fun execute(params: Unit): Flow<Settings> {
        return localDataStore.observeSettings()
            .flowOn(dispatcherProvider.main)
    }
}
