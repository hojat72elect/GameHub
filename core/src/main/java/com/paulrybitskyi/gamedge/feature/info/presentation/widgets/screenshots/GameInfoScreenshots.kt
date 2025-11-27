package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.screenshots

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.paulrybitskyi.gamedge.common.ui.images.defaultImageRequest
import com.paulrybitskyi.gamedge.common.ui.images.secondaryImage
import com.paulrybitskyi.gamedge.common.ui.theme.GamedgeTheme
import com.paulrybitskyi.gamedge.common.ui.widgets.GamedgeCard
import com.paulrybitskyi.gamedge.core.R
import com.paulrybitskyi.gamedge.feature.info.presentation.widgets.utils.GameInfoSectionWithInnerList

@Composable
internal fun GameInfoScreenshots(
    screenshots: List<GameInfoScreenshotUiModel>,
    onScreenshotClicked: (screenshotIndex: Int) -> Unit,
) {
    GameInfoSectionWithInnerList(title = stringResource(R.string.game_info_screenshots_title)) {
        itemsIndexed(
            items = screenshots,
            key = { _, screenshot -> screenshot.id },
        ) { index, screenshot ->
            Screenshot(
                screenshot = screenshot,
                modifier = Modifier.size(width = 268.dp, height = 150.dp),
                onScreenshotClicked = { onScreenshotClicked(index) },
            )
        }
    }
}

@Composable
private fun Screenshot(
    screenshot: GameInfoScreenshotUiModel,
    modifier: Modifier,
    onScreenshotClicked: () -> Unit,
) {
    GamedgeCard(
        onClick = onScreenshotClicked,
        modifier = modifier,
        shape = GamedgeTheme.shapes.medium,
        backgroundColor = Color.Transparent,
    ) {
        AsyncImage(
            model = defaultImageRequest(screenshot.url) {
                secondaryImage(R.drawable.game_landscape_placeholder)
            },
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
    }
}

@PreviewLightDark
@Composable
private fun GameInfoScreenshotsPreview() {
    GamedgeTheme {
        GameInfoScreenshots(
            screenshots = listOf(
                GameInfoScreenshotUiModel(
                    id = "1",
                    url = "",
                ),
                GameInfoScreenshotUiModel(
                    id = "2",
                    url = "",
                ),
                GameInfoScreenshotUiModel(
                    id = "3",
                    url = "",
                ),
            ),
            onScreenshotClicked = {},
        )
    }
}
