
package com.paulrybitskyi.gamedge.feature.discovery

import com.paulrybitskyi.gamedge.common.ui.R
import com.paulrybitskyi.gamedge.feature.discovery.di.GamesDiscoveryKey

internal val GamesDiscoveryCategory.titleId: Int
    get() = when (this) {
        GamesDiscoveryCategory.POPULAR -> R.string.games_category_popular
        GamesDiscoveryCategory.RECENTLY_RELEASED -> R.string.games_category_recently_released
        GamesDiscoveryCategory.COMING_SOON -> R.string.games_category_coming_soon
        GamesDiscoveryCategory.MOST_ANTICIPATED -> R.string.games_category_most_anticipated
    }

internal fun GamesDiscoveryCategory.toKeyType(): GamesDiscoveryKey.Type {
    return when (this) {
        GamesDiscoveryCategory.POPULAR -> GamesDiscoveryKey.Type.POPULAR
        GamesDiscoveryCategory.RECENTLY_RELEASED -> GamesDiscoveryKey.Type.RECENTLY_RELEASED
        GamesDiscoveryCategory.COMING_SOON -> GamesDiscoveryKey.Type.COMING_SOON
        GamesDiscoveryCategory.MOST_ANTICIPATED -> GamesDiscoveryKey.Type.MOST_ANTICIPATED
    }
}
