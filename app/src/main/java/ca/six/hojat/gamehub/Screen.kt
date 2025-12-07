package ca.six.hojat.gamehub

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import ca.six.hojat.gamehub.feature.category.CategoryScreenRoute
import ca.six.hojat.gamehub.feature.discovery.DiscoverScreenRoute
import ca.six.hojat.gamehub.feature.image.viewer.ImageViewerScreenRoute
import ca.six.hojat.gamehub.feature.info.presentation.GameInfoScreenRoute
import ca.six.hojat.gamehub.feature.likes.presentation.LikesScreenRoute
import ca.six.hojat.gamehub.feature.news.presentation.NewsScreenRoute
import ca.six.hojat.gamehub.feature.search.presentation.SearchScreenRoute
import ca.six.hojat.gamehub.feature.settings.presentation.SettingsScreenRoute
import kotlin.reflect.KClass

internal val START_SCREEN = Screen.DiscoverScreen

internal sealed class Screen(val routeClass: KClass<*>) {

    data object DiscoverScreen : Screen(DiscoverScreenRoute::class)
    data object LikesScreen : Screen(LikesScreenRoute::class)
    data object NewsScreen : Screen(NewsScreenRoute::class)
    data object SettingsScreen : Screen(SettingsScreenRoute::class)
    data object SearchScreen : Screen(SearchScreenRoute::class)
    data object CategoryScreen : Screen(CategoryScreenRoute::class) {

        fun createRoute(category: String) = CategoryScreenRoute(category = category)
    }

    data object GameInfoScreen : Screen(GameInfoScreenRoute::class) {

        fun createRoute(gameId: Int) = GameInfoScreenRoute(gameId = gameId)
    }

    data object ImageViewerScreen : Screen(ImageViewerScreenRoute::class) {

        fun createRoute(
            imageUrls: List<String>,
            title: String? = null,
            initialPosition: Int = 0,
        ): ImageViewerScreenRoute {
            return ImageViewerScreenRoute(
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
                route.contains(DiscoverScreen.route) -> DiscoverScreen
                route.contains(LikesScreen.route) -> LikesScreen
                route.contains(NewsScreen.route) -> NewsScreen
                route.contains(SettingsScreen.route) -> SettingsScreen
                route.contains(SearchScreen.route) -> SearchScreen
                route.contains(CategoryScreen.route) -> CategoryScreen
                route.contains(GameInfoScreen.route) -> GameInfoScreen
                route.contains(ImageViewerScreen.route) -> ImageViewerScreen
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
