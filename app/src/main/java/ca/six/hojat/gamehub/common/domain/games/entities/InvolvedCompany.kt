package ca.six.hojat.gamehub.common.domain.games.entities

data class InvolvedCompany(
    val company: Company,
    val isDeveloper: Boolean,
    val isPublisher: Boolean,
    val isPorter: Boolean,
    val isSupporting: Boolean,
)
