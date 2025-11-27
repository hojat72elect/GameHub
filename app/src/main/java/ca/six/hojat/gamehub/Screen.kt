package ca.six.hojat.gamehub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import ca.six.hojat.gamehub.feature.category.GamesCategoryRoute
import ca.six.hojat.gamehub.feature.discovery.GamesDiscoveryRoute
import ca.six.hojat.gamehub.feature.image.viewer.ImageViewerRoute
import ca.six.hojat.gamehub.feature.info.presentation.GameInfoRoute
import ca.six.hojat.gamehub.feature.likes.presentation.LikedGamesRoute
import ca.six.hojat.gamehub.feature.news.presentation.GamingNewsRoute
import ca.six.hojat.gamehub.feature.search.presentation.GamesSearchRoute
import ca.six.hojat.gamehub.feature.settings.presentation.SettingsRoute
import kotlin.reflect.KClass

internal val START_SCREEN = Screen.GamesDiscovery

internal sealed class Screen(val routeClass: KClass<*>) {

    data object GamesDiscovery : Screen(GamesDiscoveryRoute::class)
    data object LikedGames : Screen(LikedGamesRoute::class)
    data object GamingNews : Screen(GamingNewsRoute::class)
    data object Settings : Screen(SettingsRoute::class)
    data object GamesSearch : Screen(GamesSearchRoute::class)
    data object GamesCategory : Screen(GamesCategoryRoute::class) {

        fun createRoute(category: String): GamesCategoryRoute {
            return GamesCategoryRoute(category = category)
        }
    }

    data object GameInfo : Screen(GameInfoRoute::class) {

        fun createRoute(gameId: Int): GameInfoRoute {
            return GameInfoRoute(gameId = gameId)
        }
    }

    data object ImageViewer : Screen(ImageViewerRoute::class) {

        fun createRoute(
            imageUrls: List<String>,
            title: String? = null,
            initialPosition: Int = 0,
        ): ImageViewerRoute {
            return ImageViewerRoute(
                imageUrls = imageUrls,
                title = title,
                initialPosition = initialPosition,
            )
        }
    }

    val route: String
        get() = routeClass.qualifiedName!!

    internal companion object {

        val Saver = Saver(
            save = { it.route },
            restore = ::forRoute,
        )

        fun forRoute(route: String): Screen {
            return when {
                route.contains(GamesDiscovery.route) -> GamesDiscovery
                route.contains(LikedGames.route) -> LikedGames
                route.contains(GamingNews.route) -> GamingNews
                route.contains(Settings.route) -> Settings
                route.contains(GamesSearch.route) -> GamesSearch
                route.contains(GamesCategory.route) -> GamesCategory
                route.contains(GameInfo.route) -> GameInfo
                route.contains(ImageViewer.route) -> ImageViewer
                else -> error("Cannot find screen for the route: $route.")
            }
        }
    }
}

@Stable
@Composable
internal fun NavController.currentScreenAsState(): State<Screen> {
    val selectedScreen = rememberSaveable(stateSaver = Screen.Saver) {
        mutableStateOf(START_SCREEN)
    }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedScreen.value = Screen.forRoute(checkNotNull(destination.requireRoute()))
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedScreen
}
