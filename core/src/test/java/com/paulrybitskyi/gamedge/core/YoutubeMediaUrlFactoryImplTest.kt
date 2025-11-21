
package com.paulrybitskyi.gamedge.core

import com.google.common.truth.Truth.assertThat
import com.paulrybitskyi.gamedge.common.domain.games.entities.Video
import com.paulrybitskyi.gamedge.core.factories.YoutubeMediaUrlFactoryImpl
import com.paulrybitskyi.gamedge.core.factories.YoutubeThumbnailSize
import org.junit.Before
import org.junit.Test

private val VIDEO = Video(
    id = "id",
    name = "name",
)

internal class YoutubeMediaUrlFactoryImplTest {

    private lateinit var SUT: YoutubeMediaUrlFactoryImpl

    @Before
    fun setup() {
        SUT = YoutubeMediaUrlFactoryImpl()
    }

    @Test
    fun `Creates thumbnail image urls correctly`() {
        for (ytThumbnailSize in YoutubeThumbnailSize.entries) {
            assertThat(SUT.createThumbnailUrl(VIDEO, ytThumbnailSize))
                .isEqualTo("https://img.youtube.com/vi/${VIDEO.id}/${ytThumbnailSize.rawSize}.jpg")
        }
    }

    @Test
    fun `Returns null when video id is blank while creating thumbnail image url`() {
        assertThat(SUT.createThumbnailUrl(VIDEO.copy(id = "   "), YoutubeThumbnailSize.MEDIUM)).isNull()
    }

    @Test
    fun `Creates video urls correctly`() {
        for (ytThumbnailSize in YoutubeThumbnailSize.entries) {
            assertThat(SUT.createVideoUrl(VIDEO))
                .isEqualTo("https://youtu.be/${VIDEO.id}")
        }
    }

    @Test
    fun `Returns null when video id is blank while creating video url`() {
        assertThat(SUT.createVideoUrl(VIDEO.copy(id = "   "))).isNull()
    }
}
