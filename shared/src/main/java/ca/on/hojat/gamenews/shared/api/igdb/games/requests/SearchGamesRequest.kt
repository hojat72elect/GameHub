package ca.on.hojat.gamenews.shared.api.igdb.games.requests

import ca.on.hojat.gamenews.shared.api.common.ApiRequest

data class SearchGamesRequest(
    val searchQuery: String,
    override val offset: Int,
    override val limit: Int,
) : ApiRequest