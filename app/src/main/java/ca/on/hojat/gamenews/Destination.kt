package ca.on.hojat.gamenews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import ca.on.hojat.gamenews.core.extensions.toCsv
import java.net.URLEncoder

internal val START_DESTINATION = Destination.Discover

internal sealed class Destination(val route: String) {
    object Discover : Destination("discover")
    object Likes : Destination("likes")
    object News : Destination("news")
    object Settings : Destination("settings")
    object Search : Destination("search")

    /**
     * The page that user will head to if they click on "SEE ALL" section
     * of each of categories in "Discover" page.
     */
    object Category : Destination("discover/{${Parameters.CATEGORY}}") {
        object Parameters {
            const val CATEGORY = "category"
        }

        fun createLink(category: String): String {
            return "discover/$category"
        }
    }

    /**
     * The page that user will end up in when they click on each
     * one of the items in "Likes" or "Discover" pages.
     */
    object InfoPage : Destination("info-page/{${Parameters.GAME_ID}}") {
        object Parameters {
            const val GAME_ID = "game-id"
        }

        fun createLink(gameId: Int): String {
            return "info-page/$gameId"
        }
    }

    /**
     * if in the [InfoPage] you click on any images, you will go here.
     */
    object ImageViewer : Destination(
        "image-viewer?" +
                "${Parameters.TITLE}={${Parameters.TITLE}}&" +
                "${Parameters.INITIAL_POSITION}={${Parameters.INITIAL_POSITION}}&" +
                "${Parameters.IMAGE_URLS}={${Parameters.IMAGE_URLS}}"
    ) {
        object Parameters {
            const val TITLE = "title"
            const val INITIAL_POSITION = "initial-position"
            const val IMAGE_URLS = "image-urls"
        }

        fun createLink(
            title: String?,
            initialPosition: Int,
            imageUrls: List<String>,
        ): String {
            val modifiedImageUrls = imageUrls
                .map { imageUrl -> URLEncoder.encode(imageUrl, "UTF-8") }
                .toCsv()

            return buildString {
                append("image-viewer?")

                if (title != null) {
                    append("${Parameters.TITLE}=$title&")
                }

                append("${Parameters.INITIAL_POSITION}=$initialPosition&")
                append("${Parameters.IMAGE_URLS}=$modifiedImageUrls")
            }
        }
    }

    internal companion object {

        val Saver = Saver(
            save = { it.route },
            restore = Companion::forRoute,
        )

        fun forRoute(route: String): Destination {
            return when (route) {
                Discover.route -> Discover
                Likes.route -> Likes
                News.route -> News
                Settings.route -> Settings
                Search.route -> Search
                Category.route -> Category
                InfoPage.route -> InfoPage
                ImageViewer.route -> ImageViewer
                else -> error("Cannot find screen for the route: $route.")
            }
        }
    }
}

@Stable
@Composable
internal fun NavController.currentDestinationAsState(): State<Destination> {
    val selectedDestination = rememberSaveable(stateSaver = Destination.Saver) {
        mutableStateOf(START_DESTINATION)
    }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedDestination.value =
                Destination.forRoute(checkNotNull(destination.requireRoute()))
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedDestination
}