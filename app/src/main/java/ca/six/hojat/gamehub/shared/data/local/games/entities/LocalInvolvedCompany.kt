package ca.six.hojat.gamehub.shared.data.local.games.entities

import kotlinx.serialization.Serializable

@Serializable
data class LocalInvolvedCompany(
    val company: LocalCompany,
    val isDeveloper: Boolean,
    val isPublisher: Boolean,
    val isPorter: Boolean,
    val isSupporting: Boolean,
)
