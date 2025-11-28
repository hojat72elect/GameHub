package ca.six.hojat.gamehub.core.providers

import android.content.Context
import ca.six.hojat.gamehub.common.extensions.isConnectedToNetwork
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface NetworkStateProvider {

    val isNetworkAvailable: Boolean
}

internal class NetworkStateProviderImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : NetworkStateProvider {

    override val isNetworkAvailable: Boolean
        get() = context.isConnectedToNetwork
}
