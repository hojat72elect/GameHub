
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.links

import com.paulrybitskyi.gamedge.common.domain.games.entities.Website
import com.paulrybitskyi.gamedge.common.domain.games.entities.WebsiteCategory
import com.paulrybitskyi.gamedge.core.providers.WebsiteIconProvider
import com.paulrybitskyi.gamedge.core.providers.WebsiteNameProvider
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GameInfoLinkUiModelMapper {
    fun mapToUiModel(website: Website): GameInfoLinkUiModel?
    fun mapToUiModels(websites: List<Website>): List<GameInfoLinkUiModel>
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class GameInfoLinkUiModelMapperImpl @Inject constructor(
    private val websiteNameProvider: WebsiteNameProvider,
    private val websiteIconProvider: WebsiteIconProvider,
) : GameInfoLinkUiModelMapper {

    override fun mapToUiModel(website: Website): GameInfoLinkUiModel? {
        if (website.category == WebsiteCategory.UNKNOWN) return null

        return GameInfoLinkUiModel(
            id = website.id,
            text = websiteNameProvider.provideWebsiteName(website),
            iconId = websiteIconProvider.provideIconIdForWebsite(website),
            url = website.url,
        )
    }

    override fun mapToUiModels(websites: List<Website>): List<GameInfoLinkUiModel> {
        if (websites.isEmpty()) return emptyList()

        return websites
            .sortedBy { it.category.orderPosition }
            .mapNotNull(::mapToUiModel)
    }

    @Suppress("MagicNumber")
    private val WebsiteCategory.orderPosition: Int
        get() = when (this) {
            WebsiteCategory.UNKNOWN -> -1
            WebsiteCategory.STEAM -> 0
            WebsiteCategory.GOG -> 1
            WebsiteCategory.EPIC_GAMES -> 2
            WebsiteCategory.GOOGLE_PLAY -> 3
            WebsiteCategory.APP_STORE -> 4
            WebsiteCategory.OFFICIAL -> 5
            WebsiteCategory.TWITTER -> 6
            WebsiteCategory.SUBREDDIT -> 7
            WebsiteCategory.YOUTUBE -> 8
            WebsiteCategory.TWITCH -> 9
            WebsiteCategory.INSTAGRAM -> 10
            WebsiteCategory.FACEBOOK -> 11
            WebsiteCategory.WIKIPEDIA -> 12
            WebsiteCategory.WIKIA -> 13
            WebsiteCategory.DISCORD -> 14
        }
}
