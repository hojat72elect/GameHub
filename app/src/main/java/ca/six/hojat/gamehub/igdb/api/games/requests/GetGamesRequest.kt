package ca.six.hojat.gamehub.igdb.api.games.requests

import ca.six.hojat.gamehub.common.api.ApiRequest

data class GetGamesRequest(
    val gameIds: List<Int>,
    override val offset: Int,
    override val limit: Int,
) : ApiRequest
