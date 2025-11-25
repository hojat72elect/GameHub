package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks

import com.paulrybitskyi.gamedge.common.domain.games.entities.Image
import com.paulrybitskyi.gamedge.core.factories.IgdbImageSize
import com.paulrybitskyi.gamedge.core.factories.IgdbImageUrlFactory
import javax.inject.Inject

internal interface GameInfoArtworkUiModelMapper {
    fun mapToUiModel(image: Image): GameInfoArtworkUiModel
}

internal class GameInfoArtworkUiModelMapperImpl @Inject constructor(
    private val igdbImageUrlFactory: IgdbImageUrlFactory,
) : GameInfoArtworkUiModelMapper {

    override fun mapToUiModel(image: Image): GameInfoArtworkUiModel {
        return igdbImageUrlFactory.createUrl(
            image = image,
            config = IgdbImageUrlFactory.Config(IgdbImageSize.BIG_SCREENSHOT),
        )
        ?.let { url -> GameInfoArtworkUiModel.UrlImage(id = image.id, url = url) }
        ?: GameInfoArtworkUiModel.DefaultImage
    }
}

internal fun GameInfoArtworkUiModelMapper.mapToUiModels(
    images: List<Image>,
): List<GameInfoArtworkUiModel> {
    if (images.isEmpty()) return listOf(GameInfoArtworkUiModel.DefaultImage)

    return images.map(::mapToUiModel)
        .filterIsInstance<GameInfoArtworkUiModel.UrlImage>()
}
