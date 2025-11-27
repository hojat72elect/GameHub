package ca.six.hojat.gamehub.igdb.api.games.requests

import ca.six.hojat.gamehub.common.api.ApiRequest

data class GetPopularGamesRequest(
    val minReleaseDateTimestamp: Long,
    override val offset: Int,
    override val limit: Int,
) : ApiRequest
