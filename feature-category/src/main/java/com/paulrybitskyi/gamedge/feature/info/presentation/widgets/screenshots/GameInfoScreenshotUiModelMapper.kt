
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots

import com.paulrybitskyi.gamedge.common.domain.games.entities.Image
import com.paulrybitskyi.gamedge.core.factories.IgdbImageSize
import com.paulrybitskyi.gamedge.core.factories.IgdbImageUrlFactory
import com.paulrybitskyi.hiltbinder.BindType
import javax.inject.Inject

internal interface GameInfoScreenshotUiModelMapper {
    fun mapToUiModel(image: Image): GameInfoScreenshotUiModel?
}

@BindType(installIn = BindType.Component.VIEW_MODEL)
internal class GameInfoScreenshotUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameInfoScreenshotUiModelMapper {

    override fun mapToUiModel(image: Image): GameInfoScreenshotUiModel? {
        val screenshotUrl = igdbImageUrlFactory.createUrl(
            image,
            IgdbImageUrlFactory.Config(IgdbImageSize.MEDIUM_SCREENSHOT),
        ) ?: return null

        return GameInfoScreenshotUiModel(
            id = image.id,
            url = screenshotUrl,
        )
    }
}

internal fun GameInfoScreenshotUiModelMapper.mapToUiModels(
    images: List<Image>,
): List<GameInfoScreenshotUiModel> {
    if (images.isEmpty()) return emptyList()

    return images.mapNotNull(::mapToUiModel)
}
