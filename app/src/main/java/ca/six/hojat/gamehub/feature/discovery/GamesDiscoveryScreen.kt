package ca.six.hojat.gamehub.feature.discovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.ui.CommandsHandler
import ca.six.hojat.gamehub.common.ui.DirectionsHandler
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.common.ui.widgets.RefreshableContent
import ca.six.hojat.gamehub.common.ui.widgets.categorypreview.GamesCategoryPreview
import ca.six.hojat.gamehub.common.ui.widgets.toolbars.Toolbar
import ca.six.hojat.gamehub.feature.discovery.widgets.GamesDiscoveryItemGameUiModel
import ca.six.hojat.gamehub.feature.discovery.widgets.mapToCategoryUiModels
import ca.six.hojat.gamehub.feature.discovery.widgets.mapToDiscoveryUiModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Intentional delay to keep the pull refresh visible
// because as soon as it is let go, it disappears instantaneously.
private const val PullRefreshIntentionalDelay = 300L

@Composable
fun GamesDiscoveryScreen(
    modifier: Modifier,
    onNavigate: (Direction) -> Unit,
) {
    GamesDiscoveryScreen(
        viewModel = hiltViewModel(),
        modifier = modifier,
        onNavigate = onNavigate,
    )
}

@Composable
private fun GamesDiscoveryScreen(
    viewModel: GamesDiscoveryViewModel,
    modifier: Modifier,
    onNavigate: (Direction) -> Unit,
) {
    CommandsHandler(viewModel = viewModel)
    DirectionsHandler(viewModel = viewModel, onNavigate = onNavigate)
    GamesDiscoveryScreen(
        items = viewModel.items.collectAsState().value,
        onCategoryMoreButtonClicked = viewModel::onCategoryMoreButtonClicked,
        onSearchButtonClicked = viewModel::onSearchButtonClicked,
        onCategoryGameClicked = viewModel::onCategoryGameClicked,
        onRefreshRequested = viewModel::onRefreshRequested,
        modifier = modifier,
    )
}

@Composable
private fun GamesDiscoveryScreen(
    items: List<GamesDiscoveryItemUiModel>,
    onSearchButtonClicked: () -> Unit,
    onCategoryMoreButtonClicked: (category: String) -> Unit,
    onCategoryGameClicked: (GamesDiscoveryItemGameUiModel) -> Unit,
    onRefreshRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        modifier = modifier,
        topBar = {
            Toolbar(
                title = stringResource(R.string.games_discovery_toolbar_title),
                rightButtonIcon = painterResource(R.drawable.magnify),
                onRightButtonClick = onSearchButtonClicked,
            )
        },
    ) { paddingValues ->
        var isRefreshing by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        RefreshableContent(
            isRefreshing = isRefreshing,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onRefreshRequested = {
                isRefreshing = true

                coroutineScope.launch {
                    delay(PullRefreshIntentionalDelay)
                    onRefreshRequested()
                    isRefreshing = false
                }
            },
        ) {
            CategoryPreviewItems(
                items = items,
                onCategoryMoreButtonClicked = onCategoryMoreButtonClicked,
                onCategoryGameClicked = onCategoryGameClicked,
            )
        }
    }
}

@Composable
private fun CategoryPreviewItems(
    items: List<GamesDiscoveryItemUiModel>,
    onCategoryMoreButtonClicked: (category: String) -> Unit,
    onCategoryGameClicked: (GamesDiscoveryItemGameUiModel) -> Unit,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(GamedgeTheme.spaces.spacing_3_5),
    ) {
        items(items = items, key = GamesDiscoveryItemUiModel::id) { item ->
            val categoryGames = remember(item.games) {
                item.games.mapToCategoryUiModels()
            }

            GamesCategoryPreview(
                title = item.title,
                isProgressBarVisible = item.isProgressBarVisible,
                games = categoryGames,
                onCategoryGameClicked = { onCategoryGameClicked(it.mapToDiscoveryUiModel()) },
                onCategoryMoreButtonClicked = { onCategoryMoreButtonClicked(item.categoryName) },
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GamesDiscoveryScreenSuccessStatePreview() {
    val games = listOf(
        "Ghost of Tsushima: Director's Cut",
        "Outer Wilds: Echoes of the Eye",
        "Kena: Bridge of Spirits",
        "Forza Horizon 5",
    )
        .mapIndexed { index, gameTitle ->
            GamesDiscoveryItemGameUiModel(id = index, title = gameTitle, coverUrl = null)
        }

    val items = GamesDiscoveryCategory.entries.map { category ->
        GamesDiscoveryItemUiModel(
            id = category.id,
            categoryName = category.name,
            title = stringResource(category.titleId),
            isProgressBarVisible = true,
            games = games,
        )
    }

    GamedgeTheme {
        GamesDiscoveryScreen(
            items = items,
            onSearchButtonClicked = {},
            onCategoryMoreButtonClicked = {},
            onCategoryGameClicked = {},
            onRefreshRequested = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GamesDiscoveryScreenEmptyStatePreview() {
    val items = GamesDiscoveryCategory.entries.map { category ->
        GamesDiscoveryItemUiModel(
            id = category.id,
            categoryName = category.name,
            title = stringResource(category.titleId),
            isProgressBarVisible = true,
            games = emptyList(),
        )
    }

    GamedgeTheme {
        GamesDiscoveryScreen(
            items = items,
            onSearchButtonClicked = {},
            onCategoryMoreButtonClicked = {},
            onCategoryGameClicked = {},
            onRefreshRequested = {},
        )
    }
}
