package ca.six.hojat.gamehub.common.domain.common.entities

private const val DEFAULT_PAGE_SIZE = 20

data class Pagination(
    val offset: Int = 0,
    val limit: Int = DEFAULT_PAGE_SIZE,
)

fun Pagination.hasDefaultLimit(): Boolean {
    return (limit == DEFAULT_PAGE_SIZE)
}

fun Pagination.nextOffset(): Pagination {
    return copy(offset = (offset + DEFAULT_PAGE_SIZE))
}

fun Pagination.nextLimit(): Pagination {
    return copy(limit = (limit + DEFAULT_PAGE_SIZE))
}
