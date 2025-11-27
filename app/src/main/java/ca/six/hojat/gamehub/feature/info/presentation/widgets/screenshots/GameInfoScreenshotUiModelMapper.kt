package ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots

import ca.six.hojat.gamehub.common.domain.games.entities.Image
import ca.six.hojat.gamehub.core.factories.IgdbImageSize
import ca.six.hojat.gamehub.core.factories.IgdbImageUrlFactory
import javax.inject.Inject

internal interface GameInfoScreenshotUiModelMapper {
    fun mapToUiModel(image: Image): GameInfoScreenshotUiModel?
}

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
