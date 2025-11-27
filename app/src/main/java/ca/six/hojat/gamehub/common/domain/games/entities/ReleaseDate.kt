package ca.six.hojat.gamehub.common.domain.games.entities

data class ReleaseDate(
    val date: Long?,
    val year: Int?,
    val category: ReleaseDateCategory,
)
