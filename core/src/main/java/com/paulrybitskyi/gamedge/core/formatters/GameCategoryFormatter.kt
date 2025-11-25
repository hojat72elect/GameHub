package com.paulrybitskyi.gamedge.core.formatters

import com.paulrybitskyi.gamedge.common.domain.games.entities.Category
import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import javax.inject.Inject

interface GameCategoryFormatter {
    fun formatCategory(category: Category): String
}

internal class GameCategoryFormatterImpl @Inject constructor(
    private val stringProvider: StringProvider,
) : GameCategoryFormatter {

    override fun formatCategory(category: Category): String {
        return stringProvider.getString(
            when (category) {
                Category.UNKNOWN -> R.string.not_available_abbr
                Category.MAIN_GAME -> R.string.game_category_main
                Category.BUNDLE -> R.string.game_category_bundle
                Category.MOD -> R.string.game_category_mod
                Category.EPISODE -> R.string.game_category_episode
                Category.SEASON -> R.string.game_category_season

                Category.DLC,
                Category.EXPANSION,
                Category.STANDALONE_EXPANSION,
                    -> R.string.game_category_dlc
            },
        )
    }
}
