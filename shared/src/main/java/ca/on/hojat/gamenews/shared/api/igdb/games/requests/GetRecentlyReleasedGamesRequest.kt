package ca.on.hojat.gamenews.shared.api.igdb.games.requests

import ca.on.hojat.gamenews.shared.api.common.ApiRequest

data class GetRecentlyReleasedGamesRequest(
    val minReleaseDateTimestamp: Long,
    val maxReleaseDateTimestamp: Long,
    override val offset: Int,
    override val limit: Int,
) : ApiRequest