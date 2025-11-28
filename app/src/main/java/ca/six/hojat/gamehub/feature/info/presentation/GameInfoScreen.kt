package ca.six.hojat.gamehub.feature.info.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import ca.six.hojat.gamehub.R
import ca.six.hojat.gamehub.common.extensions.showShortToast
import ca.six.hojat.gamehub.common.ui.CommandsHandler
import ca.six.hojat.gamehub.common.ui.DirectionsHandler
import ca.six.hojat.gamehub.common.ui.LocalUrlOpener
import ca.six.hojat.gamehub.common.ui.OnLifecycleEvent
import ca.six.hojat.gamehub.common.ui.base.events.Direction
import ca.six.hojat.gamehub.common.ui.rememberWindowInsetsController
import ca.six.hojat.gamehub.common.ui.theme.GamedgeTheme
import ca.six.hojat.gamehub.common.ui.theme.darkScrim
import ca.six.hojat.gamehub.common.ui.widgets.AnimatedContentContainer
import ca.six.hojat.gamehub.common.ui.widgets.FiniteUiState
import ca.six.hojat.gamehub.common.ui.widgets.GamedgeProgressIndicator
import ca.six.hojat.gamehub.common.ui.widgets.Info
import ca.six.hojat.gamehub.common.ui.widgets.categorypreview.GamesCategoryPreview
import ca.six.hojat.gamehub.feature.info.presentation.widgets.GameInfoSummary
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanies
import ca.six.hojat.gamehub.feature.info.presentation.widgets.companies.GameInfoCompanyUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetails
import ca.six.hojat.gamehub.feature.info.presentation.widgets.details.GameInfoDetailsUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeader
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.GameInfoHeaderUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.header.artworks.GameInfoArtworkUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinkUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.links.GameInfoLinks
import ca.six.hojat.gamehub.feature.info.presentation.widgets.main.GameInfoUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGameUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesType
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.GameInfoRelatedGamesUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mapToCategoryUiModels
import ca.six.hojat.gamehub.feature.info.presentation.widgets.relatedgames.mapToInfoRelatedGameUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshotUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.screenshots.GameInfoScreenshots
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideoUiModel
import ca.six.hojat.gamehub.feature.info.presentation.widgets.videos.GameInfoVideos

@Composable
fun GameInfoScreen(
    route: GameInfoRoute,
    onNavigate: (Direction) -> Unit,
) {
    GameInfoScreen(
        viewModel = hiltViewModel<GameInfoViewModel, GameInfoViewModel.Factory>(
            creationCallback = { factory -> factory.create(route) },
        ),
        onNavigate = onNavigate,
    )
}

@Composable
private fun GameInfoScreen(
    viewModel: GameInfoViewModel,
    onNavigate: (Direction) -> Unit,
) {
    val urlOpener = LocalUrlOpener.current
    val context = LocalContext.current

    CommandsHandler(viewModel = viewModel) { command ->
        when (command) {
            is GameInfoCommand.OpenUrl -> {
                if (!urlOpener.openUrl(command.url, context)) {
                    context.showShortToast(context.getString(R.string.url_opener_not_found))
                }
            }
        }
    }
    DirectionsHandler(viewModel = viewModel, onNavigate = onNavigate)
    StatusBarHandler()
    GameInfoScreen(
        uiState = viewModel.uiState.collectAsState().value,
        onArtworkClicked = viewModel::onArtworkClicked,
        onBackButtonClicked = viewModel::onBackButtonClicked,
        onCoverClicked = viewModel::onCoverClicked,
        onLikeButtonClicked = viewModel::onLikeButtonClicked,
        onVideoClicked = viewModel::onVideoClicked,
        onScreenshotClicked = viewModel::onScreenshotClicked,
        onLinkClicked = viewModel::onLinkClicked,
        onCompanyClicked = viewModel::onCompanyClicked,
        onRelatedGameClicked = viewModel::onRelatedGameClicked,
    )
}

@Composable
private fun StatusBarHandler() {
    val windowsInsetsController = rememberWindowInsetsController()
    val wereStatusBarIconsDark = remember { windowsInsetsController?.isAppearanceLightStatusBars }

    OnLifecycleEvent(
        onStart = {
            // Make the status bar's icons always white on this screen
            windowsInsetsController?.isAppearanceLightStatusBars = false
        },
        onStop = {
            if (wereStatusBarIconsDark != null) {
                windowsInsetsController?.isAppearanceLightStatusBars = wereStatusBarIconsDark
            }
        },
    )
}

@Composable
private fun GameInfoScreen(
    uiState: GameInfoUiState,
    onArtworkClicked: (artworkIndex: Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onCoverClicked: () -> Unit,
    onLikeButtonClicked: () -> Unit,
    onVideoClicked: (GameInfoVideoUiModel) -> Unit,
    onScreenshotClicked: (screenshotIndex: Int) -> Unit,
    onLinkClicked: (GameInfoLinkUiModel) -> Unit,
    onCompanyClicked: (GameInfoCompanyUiModel) -> Unit,
    onRelatedGameClicked: (GameInfoRelatedGameUiModel) -> Unit,
) {
    Scaffold { paddingValues ->
        AnimatedContentContainer(
            finiteUiState = uiState.finiteUiState,
            modifier = Modifier.padding(paddingValues),
        ) { finiteUiState ->
            when (finiteUiState) {
                FiniteUiState.Empty -> {
                    EmptyState(
                        modifier = Modifier
                            .systemBarsPadding()
                            .align(Alignment.Center),
                    )
                }

                FiniteUiState.Loading -> {
                    LoadingState(
                        modifier = Modifier
                            .systemBarsPadding()
                            .align(Alignment.Center),
                    )
                }

                FiniteUiState.Success -> {
                    SuccessState(
                        gameInfo = checkNotNull(uiState.game),
                        contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                        onArtworkClicked = onArtworkClicked,
                        onBackButtonClicked = onBackButtonClicked,
                        onCoverClicked = onCoverClicked,
                        onLikeButtonClicked = onLikeButtonClicked,
                        onVideoClicked = onVideoClicked,
                        onScreenshotClicked = onScreenshotClicked,
                        onLinkClicked = onLinkClicked,
                        onCompanyClicked = onCompanyClicked,
                        onRelatedGameClicked = onRelatedGameClicked,
                    )
                }
            }
        }
    }

    Spacer(
        modifier = Modifier
            .windowInsetsTopHeight(WindowInsets.statusBars)
            .fillMaxWidth()
            .background(GamedgeTheme.colors.darkScrim),
    )
}

@Composable
private fun LoadingState(modifier: Modifier) {
    GamedgeProgressIndicator(modifier = modifier)
}

@Composable
private fun EmptyState(modifier: Modifier) {
    Info(
        icon = painterResource(R.drawable.gamepad_variant_outline),
        title = stringResource(R.string.game_info_info_view_title),
        modifier = modifier.padding(horizontal = GamedgeTheme.spaces.spacing_7_5),
    )
}

@Composable
private fun SuccessState(
    gameInfo: GameInfoUiModel,
    contentPadding: PaddingValues,
    onArtworkClicked: (artworkIndex: Int) -> Unit,
    onBackButtonClicked: () -> Unit,
    onCoverClicked: () -> Unit,
    onLikeButtonClicked: () -> Unit,
    onVideoClicked: (GameInfoVideoUiModel) -> Unit,
    onScreenshotClicked: (screenshotIndex: Int) -> Unit,
    onLinkClicked: (GameInfoLinkUiModel) -> Unit,
    onCompanyClicked: (GameInfoCompanyUiModel) -> Unit,
    onRelatedGameClicked: (GameInfoRelatedGameUiModel) -> Unit,
) {
    val listState = rememberLazyListState()

    GameInfoHeader(
        headerInfo = gameInfo.headerModel,
        listState = listState,
        onArtworkClicked = onArtworkClicked,
        onBackButtonClicked = onBackButtonClicked,
        onCoverClicked = onCoverClicked,
        onLikeButtonClicked = onLikeButtonClicked,
    ) { modifier ->
        val layoutDirection = LocalLayoutDirection.current
        val spacing = GamedgeTheme.spaces.spacing_3_5

        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = PaddingValues(
                start = contentPadding.calculateStartPadding(layoutDirection),
                top = contentPadding.calculateTopPadding().plus(spacing),
                end = contentPadding.calculateEndPadding(layoutDirection),
                bottom = contentPadding.calculateBottomPadding(),
            ),
            verticalArrangement = Arrangement.spacedBy(spacing),
        ) {
            if (gameInfo.hasVideos) {
                videosItem(
                    videos = gameInfo.videoModels,
                    onVideoClicked = onVideoClicked,
                )
            }

            if (gameInfo.hasScreenshots) {
                screenshotsItem(
                    screenshots = gameInfo.screenshotModels,
                    onScreenshotClicked = onScreenshotClicked,
                )
            }

            if (gameInfo.hasSummary) {
                summaryItem(model = checkNotNull(gameInfo.summary))
            }

            if (gameInfo.hasDetails) {
                detailsItem(model = checkNotNull(gameInfo.detailsModel))
            }

            if (gameInfo.hasLinks) {
                linksItem(
                    model = gameInfo.linkModels,
                    onLinkClicked = onLinkClicked,
                )
            }

            if (gameInfo.hasCompanies) {
                companiesItem(
                    model = gameInfo.companyModels,
                    onCompanyClicked = onCompanyClicked,
                )
            }

            if (gameInfo.hasOtherCompanyGames) {
                relatedGamesItem(
                    model = checkNotNull(gameInfo.otherCompanyGames),
                    onGameClicked = onRelatedGameClicked,
                )
            }

            if (gameInfo.hasSimilarGames) {
                relatedGamesItem(
                    model = checkNotNull(gameInfo.similarGames),
                    onGameClicked = onRelatedGameClicked,
                )
            }
        }
    }
}

private fun LazyListScope.videosItem(
    videos: List<GameInfoVideoUiModel>,
    onVideoClicked: (GameInfoVideoUiModel) -> Unit,
) {
    gameInfoItem(item = GameInfoItem.Videos) {
        GameInfoVideos(
            videos = videos,
            onVideClicked = onVideoClicked,
        )
    }
}

private fun LazyListScope.screenshotsItem(
    screenshots: List<GameInfoScreenshotUiModel>,
    onScreenshotClicked: (screenshotIndex: Int) -> Unit,
) {
    gameInfoItem(item = GameInfoItem.Screenshots) {
        GameInfoScreenshots(
            screenshots = screenshots,
            onScreenshotClicked = onScreenshotClicked,
        )
    }
}

private fun LazyListScope.summaryItem(model: String) {
    gameInfoItem(item = GameInfoItem.Summary) {
        GameInfoSummary(summary = model)
    }
}

private fun LazyListScope.detailsItem(model: GameInfoDetailsUiModel) {
    gameInfoItem(item = GameInfoItem.Details) {
        GameInfoDetails(details = model)
    }
}

private fun LazyListScope.linksItem(
    model: List<GameInfoLinkUiModel>,
    onLinkClicked: (GameInfoLinkUiModel) -> Unit,
) {
    gameInfoItem(item = GameInfoItem.Links) {
        GameInfoLinks(
            links = model,
            onLinkClicked = onLinkClicked,
        )
    }
}

private fun LazyListScope.companiesItem(
    model: List<GameInfoCompanyUiModel>,
    onCompanyClicked: (GameInfoCompanyUiModel) -> Unit,
) {
    gameInfoItem(item = GameInfoItem.Companies) {
        GameInfoCompanies(
            companies = model,
            onCompanyClicked = onCompanyClicked,
        )
    }
}

private fun LazyListScope.relatedGamesItem(
    model: GameInfoRelatedGamesUiModel,
    onGameClicked: (GameInfoRelatedGameUiModel) -> Unit,
) {
    gameInfoItem(
        item = when (model.type) {
            GameInfoRelatedGamesType.OTHER_COMPANY_GAMES -> GameInfoItem.OtherCompanyGames
            GameInfoRelatedGamesType.SIMILAR_GAMES -> GameInfoItem.SimilarGames
        },
    ) {
        val categoryGames = remember(model.items) {
            model.items.mapToCategoryUiModels()
        }

        GamesCategoryPreview(
            title = model.title,
            isProgressBarVisible = false,
            games = categoryGames,
            onCategoryGameClicked = {
                onGameClicked(it.mapToInfoRelatedGameUiModel())
            },
            topBarMargin = GamedgeTheme.spaces.spacing_2_5,
            isMoreButtonVisible = false,
        )
    }
}

private fun LazyListScope.gameInfoItem(
    item: GameInfoItem,
    content: @Composable LazyItemScope.() -> Unit,
) {
    item(
        key = item.key,
        contentType = item.contentType,
        content = content,
    )
}

private enum class GameInfoItem(
    val key: Int,
    val contentType: Int,
) {
    Videos(key = 1, contentType = 1),
    Screenshots(key = 2, contentType = 2),
    Summary(key = 3, contentType = 3),
    Details(key = 4, contentType = 4),
    Links(key = 5, contentType = 5),
    Companies(key = 6, contentType = 6),

    // Both other & similar games is the same composable
    // filled with different data. That's why contentType
    // is the same for them two.
    OtherCompanyGames(key = 7, contentType = 7),
    SimilarGames(key = 8, contentType = 7),
}

// TODO (02.01.2022): Currently, preview height is limited to 2k DP.
// Try to increase this value in the future to showcase all the UI.
@Preview(heightDp = 2000)
@Preview(heightDp = 2000, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun GameInfoScreenSuccessStateWithMaxUiElementsPreview() {
    GamedgeTheme {
        GameInfoScreen(
            uiState = GameInfoUiState(
                isLoading = false,
                game = buildFakeGameModel(),
            ),
            onArtworkClicked = {},
            onBackButtonClicked = {},
            onCoverClicked = {},
            onLikeButtonClicked = {},
            onVideoClicked = {},
            onScreenshotClicked = {},
            onLinkClicked = {},
            onCompanyClicked = {},
            onRelatedGameClicked = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GameInfoScreenSuccessStateWithMinUiElementsPreview() {
    val game = buildFakeGameModel()
    val strippedGame = game.copy(
        headerModel = game.headerModel.copy(
            developerName = null,
            likeCount = "0",
            gameCategory = "N/A",
        ),
        videoModels = emptyList(),
        screenshotModels = emptyList(),
        summary = null,
        detailsModel = null,
        linkModels = emptyList(),
        companyModels = emptyList(),
        otherCompanyGames = null,
        similarGames = null,
    )

    GamedgeTheme {
        GameInfoScreen(
            uiState = GameInfoUiState(
                isLoading = false,
                game = strippedGame,
            ),
            onArtworkClicked = {},
            onBackButtonClicked = {},
            onCoverClicked = {},
            onLikeButtonClicked = {},
            onVideoClicked = {},
            onScreenshotClicked = {},
            onLinkClicked = {},
            onCompanyClicked = {},
            onRelatedGameClicked = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GameInfoScreenEmptyStatePreview() {
    GamedgeTheme {
        GameInfoScreen(
            uiState = GameInfoUiState(
                isLoading = false,
                game = null,
            ),
            onArtworkClicked = {},
            onBackButtonClicked = {},
            onCoverClicked = {},
            onLikeButtonClicked = {},
            onVideoClicked = {},
            onScreenshotClicked = {},
            onLinkClicked = {},
            onCompanyClicked = {},
            onRelatedGameClicked = {},
        )
    }
}

@PreviewLightDark
@Composable
private fun GameInfoScreenLoadingStatePreview() {
    GamedgeTheme {
        GameInfoScreen(
            uiState = GameInfoUiState(
                isLoading = true,
                game = null,
            ),
            onArtworkClicked = {},
            onBackButtonClicked = {},
            onCoverClicked = {},
            onLikeButtonClicked = {},
            onVideoClicked = {},
            onScreenshotClicked = {},
            onLinkClicked = {},
            onCompanyClicked = {},
            onRelatedGameClicked = {},
        )
    }
}

@Suppress("LongMethod")
private fun buildFakeGameModel(): GameInfoUiModel {
    return GameInfoUiModel(
        id = 1,
        headerModel = GameInfoHeaderUiModel(
            artworks = listOf(GameInfoArtworkUiModel.DefaultImage),
            isLiked = true,
            coverImageUrl = null,
            title = "Elden Ring",
            releaseDate = "Feb 25, 2022 (in a month)",
            developerName = "FromSoftware",
            rating = "N/A",
            likeCount = "92",
            ageRating = "N/A",
            gameCategory = "Main",
        ),
        videoModels = listOf(
            GameInfoVideoUiModel(
                id = "1",
                thumbnailUrl = "",
                videoUrl = "",
                title = "Announcement Trailer",
            ),
            GameInfoVideoUiModel(
                id = "2",
                thumbnailUrl = "",
                videoUrl = "",
                title = "Gameplay Trailer",
            ),
        ),
        screenshotModels = listOf(
            GameInfoScreenshotUiModel(
                id = "1",
                url = "",
            ),
            GameInfoScreenshotUiModel(
                id = "2",
                url = "",
            ),
        ),
        summary = "Elden Ring is an action-RPG open world game with RPG " +
                "elements such as stats, weapons and spells.",
        detailsModel = GameInfoDetailsUiModel(
            genresText = "Role-playing (RPG)",
            platformsText = "PC (Microsoft Windows) • PlayStation 4 • " +
                    "Xbox One • PlayStation 5 • Xbox Series X|S",
            modesText = "Single player • Multiplayer • Co-operative",
            playerPerspectivesText = "Third person",
            themesText = "Action",
        ),
        linkModels = listOf(
            GameInfoLinkUiModel(
                id = 1,
                text = "Steam",
                iconId = R.drawable.steam,
                url = "",
            ),
            GameInfoLinkUiModel(
                id = 2,
                text = "Official",
                iconId = R.drawable.web,
                url = "",
            ),
            GameInfoLinkUiModel(
                id = 3,
                text = "Twitter",
                iconId = R.drawable.twitter,
                url = "",
            ),
            GameInfoLinkUiModel(
                id = 4,
                text = "Subreddit",
                iconId = R.drawable.reddit,
                url = "",
            ),
            GameInfoLinkUiModel(
                id = 5,
                text = "YouTube",
                iconId = R.drawable.youtube,
                url = "",
            ),
            GameInfoLinkUiModel(
                id = 6,
                text = "Twitch",
                iconId = R.drawable.twitch,
                url = "",
            ),
        ),
        companyModels = listOf(
            GameInfoCompanyUiModel(
                id = 1,
                logoUrl = null,
                logoWidth = 1400,
                logoHeight = 400,
                websiteUrl = "",
                name = "FromSoftware",
                roles = "Main Developer",
            ),
            GameInfoCompanyUiModel(
                id = 2,
                logoUrl = null,
                logoWidth = 500,
                logoHeight = 400,
                websiteUrl = "",
                name = "Bandai Namco Entertainment",
                roles = "Publisher",
            ),
        ),
        otherCompanyGames = GameInfoRelatedGamesUiModel(
            type = GameInfoRelatedGamesType.OTHER_COMPANY_GAMES,
            title = "More games by FromSoftware",
            items = listOf(
                GameInfoRelatedGameUiModel(
                    id = 1,
                    title = "Dark Souls",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 2,
                    title = "Dark Souls II",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 3,
                    title = "Lost Kingdoms",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 4,
                    title = "Lost Kingdoms II",
                    coverUrl = null,
                ),
            ),
        ),
        similarGames = GameInfoRelatedGamesUiModel(
            type = GameInfoRelatedGamesType.SIMILAR_GAMES,
            title = "Similar Games",
            items = listOf(
                GameInfoRelatedGameUiModel(
                    id = 1,
                    title = "Nights of Azure 2: Bride of the New Moon",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 2,
                    title = "God Eater 3",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 3,
                    title = "Shadows: Awakening",
                    coverUrl = null,
                ),
                GameInfoRelatedGameUiModel(
                    id = 3,
                    title = "SoulWorker",
                    coverUrl = null,
                ),
            ),
        ),
    )
}
