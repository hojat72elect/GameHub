package com.paulrybitskyi.gamedge.feature.settings.domain.usecases

import com.paulrybitskyi.gamedge.common.domain.common.DispatcherProvider
import com.paulrybitskyi.gamedge.common.domain.common.usecases.ObservableUseCase
import com.paulrybitskyi.gamedge.feature.settings.domain.datastores.SettingsLocalDataStore
import com.paulrybitskyi.gamedge.feature.settings.domain.entities.Settings
import com.paulrybitskyi.hiltbinder.BindType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

internal interface ObserveSettingsUseCase : ObservableUseCase<Unit, Settings>

@Singleton
@BindType
internal class ObserveSettingsUseCaseImpl @Inject constructor(
    private val localDataStore: SettingsLocalDataStore,
    private val dispatcherProvider: DispatcherProvider,
) : ObserveSettingsUseCase {

    override fun execute(params: Unit): Flow<Settings> {
        return localDataStore.observeSettings()
            .flowOn(dispatcherProvider.main)
    }
}
