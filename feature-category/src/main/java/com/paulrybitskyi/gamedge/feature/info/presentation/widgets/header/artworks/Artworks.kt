
package com.paulrybitskyi.gamedge.feature.info.presentation.widgets.header.artworks

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.paulrybitskyi.gamedge.common.ui.clickable
import com.paulrybitskyi.gamedge.common.ui.images.defaultImageRequest
import com.paulrybitskyi.gamedge.common.ui.images.secondaryImage
import com.paulrybitskyi.gamedge.common.ui.theme.GamedgeTheme
import com.paulrybitskyi.gamedge.common.ui.R as CommonUiR

@Composable
internal fun Artworks(
    artworks: List<GameInfoArtworkUiModel>,
    isScrollingEnabled: Boolean,
    modifier: Modifier,
    onArtworkChanged: (artworkIndex: Int) -> Unit,
    onArtworkClicked: (artworkIndex: Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = artworks::size)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collect { page -> onArtworkChanged(page) }
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier,
        userScrollEnabled = isScrollingEnabled,
        key = { page -> artworks[page].id },
    ) { page ->
        Artwork(
            artwork = artworks[page],
            onArtworkClicked = { onArtworkClicked(page) },
        )
    }
}

@Composable
private fun Artwork(
    artwork: GameInfoArtworkUiModel,
    onArtworkClicked: () -> Unit,
) {
    val data = when (artwork) {
        is GameInfoArtworkUiModel.DefaultImage -> CommonUiR.drawable.game_background_placeholder
        is GameInfoArtworkUiModel.UrlImage -> artwork.url
    }

    AsyncImage(
        model = defaultImageRequest(data) {
            secondaryImage(CommonUiR.drawable.game_background_placeholder)
        },
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                onClick = onArtworkClicked,
            ),
        contentScale = ContentScale.Crop,
    )
}

@Preview(heightDp = 240)
@Preview(heightDp = 240, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ArtworksPreview() {
    GamedgeTheme {
        Artworks(
            artworks = listOf(GameInfoArtworkUiModel.DefaultImage),
            isScrollingEnabled = true,
            modifier = Modifier,
            onArtworkChanged = {},
            onArtworkClicked = {},
        )
    }
}
