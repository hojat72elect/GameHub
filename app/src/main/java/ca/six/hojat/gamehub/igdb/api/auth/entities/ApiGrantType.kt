package ca.six.hojat.gamehub.igdb.api.auth.entities

enum class ApiGrantType(val rawType: String) {
    AUTHORIZATION_CODE("authorization_code"),
    CLIENT_CREDENTIALS("client_credentials"),
}
