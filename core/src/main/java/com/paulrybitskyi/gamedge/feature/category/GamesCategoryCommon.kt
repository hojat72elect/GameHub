package com.paulrybitskyi.gamedge.feature.category

import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.feature.category.di.GamesCategoryKey

internal val GamesCategory.titleId: Int
    get() = when (this) {
        GamesCategory.POPULAR -> R.string.games_category_popular
        GamesCategory.RECENTLY_RELEASED -> R.string.games_category_recently_released
        GamesCategory.COMING_SOON -> R.string.games_category_coming_soon
        GamesCategory.MOST_ANTICIPATED -> R.string.games_category_most_anticipated
    }

internal fun GamesCategory.toKeyType(): GamesCategoryKey.Type {
    return when (this) {
        GamesCategory.POPULAR -> GamesCategoryKey.Type.POPULAR
        GamesCategory.RECENTLY_RELEASED -> GamesCategoryKey.Type.RECENTLY_RELEASED
        GamesCategory.COMING_SOON -> GamesCategoryKey.Type.COMING_SOON
        GamesCategory.MOST_ANTICIPATED -> GamesCategoryKey.Type.MOST_ANTICIPATED
    }
}
