package ca.six.hojat.gamehub.igdb.api.auth

import ca.six.hojat.gamehub.igdb.api.auth.entities.ApiAuthorizationType
import javax.inject.Inject

internal class Authorizer @Inject constructor() {

    fun buildAuthorizationHeader(type: ApiAuthorizationType, token: String): String {
        return buildString {
            append(type.rawType)
            append(" ")
            append(token)
        }
    }
}
