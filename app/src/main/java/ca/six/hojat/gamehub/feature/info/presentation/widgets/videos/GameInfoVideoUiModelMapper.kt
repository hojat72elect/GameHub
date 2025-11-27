package ca.six.hojat.gamehub.feature.info.presentation.widgets.videos

import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.domain.games.entities.Video
import ca.six.hojat.gamehub.core.factories.YoutubeMediaUrlFactory
import ca.six.hojat.gamehub.core.factories.YoutubeThumbnailSize
import ca.six.hojat.gamehub.core.providers.StringProvider
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
