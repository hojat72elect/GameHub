package ca.six.hojat.gamehub.feature.news.presentation.widgets

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.ui.images.defaultImageRequest
import ca.six.hojat.gamehub.common.ui.images.secondaryImage
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.common.ui.widgets.GamedgeCard
import coil.compose.AsyncImage

@Composable
internal fun GamingNewsItem(
    model: GamingNewsItemUiModel,
    onClick: () -> Unit,
) {
    GamedgeCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(GamedgeTheme.spaces.spacing_4_0)) {
            if (model.hasImageUrl) {
                Image(
                    imageUrl = checkNotNull(model.imageUrl),
                    modifier = Modifier
                        .height(168.dp)
                        .padding(bottom = GamedgeTheme.spaces.spacing_3_5),
                )
            }

            Text(
                text = model.title,
                modifier = Modifier.fillMaxWidth(),
                color = GamedgeTheme.colors.onPrimary,
                style = GamedgeTheme.typography.subtitle2,
            )
            Text(
                text = model.lede,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = GamedgeTheme.spaces.spacing_1_5),
                style = GamedgeTheme.typography.body2.copy(
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
    modifier: Modifier,
) {
    GamedgeCard(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        shape = GamedgeTheme.shapes.medium,
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
        modifier = Modifier.padding(top = GamedgeTheme.spaces.spacing_2_5),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(R.drawable.clock_outline_16dp),
            contentDescription = null,
            modifier = Modifier.padding(end = GamedgeTheme.spaces.spacing_1_0),
        )
        Text(
            text = publicationDate,
            style = GamedgeTheme.typography.caption,
        )
    }
}

@PreviewLightDark
@Composable
private fun GamingNewsItemWithImagePreview() {
    GamedgeTheme {
        GamingNewsItem(
            model = GamingNewsItemUiModel(
                id = 1,
                imageUrl = "url",
                title = "Steam Concurrent Player Count Breaks Record Again, Tops 26 Million",
                lede = "However, the record for those actively in a game has not been broken yet.",
                publicationDate = "3 mins ago",
                siteDetailUrl = "url",
            ),
            onClick = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GamingNewsItemWithoutImagePreview() {
    GamedgeTheme {
        GamingNewsItem(
            model = GamingNewsItemUiModel(
                id = 1,
                imageUrl = null,
                title = "Steam Concurrent Player Count Breaks Record Again, Tops 26 Million",
                lede = "However, the record for those actively in a game has not been broken yet.",
                publicationDate = "3 mins ago",
                siteDetailUrl = "url",
            ),
            onClick = {},
        )
    }
}
