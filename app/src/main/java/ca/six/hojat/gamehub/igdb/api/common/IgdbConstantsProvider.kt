package ca.six.hojat.gamehub.igdb.api.common

import javax.inject.Inject

internal interface IgdbConstantsProvider {
    val apiBaseUrl: String
}

internal class ProdIgdbConstantsProvider @Inject constructor() : IgdbConstantsProvider {
    override val apiBaseUrl: String = Constants.IGDB_API_BASE_URL
}
