package ca.on.hojat.gamenews.feature_news.presentation.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ca.on.hojat.gamenews.shared.ui.images.defaultImageRequest
import ca.on.hojat.gamenews.shared.ui.images.secondaryImage
import ca.on.hojat.gamenews.shared.ui.theme.GameNewsTheme
import ca.on.hojat.gamenews.shared.ui.widgets.GameNewsCard
import coil.compose.AsyncImage
import ca.on.hojat.gamenews.R

@Composable
internal fun GamingNewsItem(
    model: GamingNewsItemUiModel,
    onClick: () -> Unit
) {
    GameNewsCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(GameNewsTheme.spaces.spacing_4_0)) {
            if (model.hasImageUrl) {
                Image(
                    imageUrl = checkNotNull(model.imageUrl),
                    modifier = Modifier
                        .height(168.dp)
                        .padding(bottom = GameNewsTheme.spaces.spacing_3_5)
                )
            }

            Text(
                text = model.title,
                modifier = Modifier.fillMaxWidth(),
                color = GameNewsTheme.colors.onPrimary,
                style = GameNewsTheme.typography.subtitle2,
            )
            Text(
                text = model.lede,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = GameNewsTheme.spaces.spacing_1_5),
                style = GameNewsTheme.typography.body2.copy(
                    lineHeight = TextUnit.Unspecified,
                ),
            )
            Timestamp(publicationDate = model.publicationDate)
        }
    }
}

@Composable
private fun Image(
    imageUrl: String,
    modifier: Modifier
) {
    GameNewsCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        shape = GameNewsTheme.shapes.medium,
        backgroundColor = Color.Transparent,
    ) {
        AsyncImage(
            model = defaultImageRequest(imageUrl) {
                secondaryImage(R.drawable.game_landscape_placeholder)
            },
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun Timestamp(publicationDate: String) {
    Row(
        modifier = Modifier.padding(top = GameNewsTheme.spaces.spacing_2_5),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(R.drawable.clock_outline_16dp),
            contentDescription = null,
            modifier = Modifier.padding(end = GameNewsTheme.spaces.spacing_1_0),
        )
        Text(
            text = publicationDate,
            style = GameNewsTheme.typography.caption,
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GamingNewsItemWithImagePreview() {
    GameNewsTheme {
        GamingNewsItem(
            model = GamingNewsItemUiModel(
                id = 1,
                imageUrl = "url",
                title = "Steam Concurrent Player Count Breaks Record Again, Tops 26 Million",
                lede = "However, the record for those actively in a game has not been broken yet.",
                publicationDate = "3 mins ago",
                siteDetailUrl = "url",
            ),
            onClick = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GamingNewsItemWithoutImagePreview() {
    GameNewsTheme {
        GamingNewsItem(
            model = GamingNewsItemUiModel(
                id = 1,
                imageUrl = null,
                title = "Steam Concurrent Player Count Breaks Record Again, Tops 26 Million",
                lede = "However, the record for those actively in a game has not been broken yet.",
                publicationDate = "3 mins ago",
                siteDetailUrl = "url",
            ),
            onClick = {}
        )
    }
}