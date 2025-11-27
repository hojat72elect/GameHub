package ca.six.hojat.gamehub.feature.settings.domain.entities

data class Settings(
    val theme: Theme,
) {
    companion object {
        val DEFAULT = Settings(
            theme = Theme.SYSTEM,
        )
    }
}
