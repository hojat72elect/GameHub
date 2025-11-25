package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.videos

import com.paulrybitskyi.gamedge.common.domain.games.entities.Video
import com.paulrybitskyi.gamedge.core.factories.YoutubeMediaUrlFactory
import com.paulrybitskyi.gamedge.core.factories.YoutubeThumbnailSize
import com.paulrybitskyi.gamedge.core.providers.StringProvider
import com.paulrybitskyi.gamedge.feature.category.R
import javax.inject.Inject

internal interface GameInfoVideoUiModelMapper {
    fun mapToUiModel(video: Video): GameInfoVideoUiModel?
}

internal class GameInfoVideoUiModelMapperImpl @Inject constructor(
    private val youtubeMediaUrlFactory: YoutubeMediaUrlFactory,
    private val stringProvider: StringProvider,
) : GameInfoVideoUiModelMapper {

    override fun mapToUiModel(video: Video): GameInfoVideoUiModel? {
        val thumbnailUrl = youtubeMediaUrlFactory.createThumbnailUrl(
            video,
            YoutubeThumbnailSize.MEDIUM,
        )
        val videoUrl = youtubeMediaUrlFactory.createVideoUrl(video)
        val videoTitle = video.name ?: stringProvider.getString(
            R.string.game_info_video_title_fallback,
        )

        if ((thumbnailUrl == null) && (videoUrl == null)) return null

        return GameInfoVideoUiModel(
            id = video.id,
            thumbnailUrl = checkNotNull(thumbnailUrl),
            videoUrl = checkNotNull(videoUrl),
            title = videoTitle,
        )
    }
}

internal fun GameInfoVideoUiModelMapper.mapToUiModels(
    videos: List<Video>,
): List<GameInfoVideoUiModel> {
    if (videos.isEmpty()) return emptyList()

    return videos.mapNotNull(::mapToUiModel)
}
