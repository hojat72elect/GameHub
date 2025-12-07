package ca.six.hojat.gamehub

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.ComposeNavigatorDestinationBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.get
import androidx.navigation.toRoute
import ca.six.hojat.gamehub.common.ui.HorizontalSliding
import ca.six.hojat.gamehub.common.ui.OvershootScaling
import ca.six.hojat.gamehub.feature.category.GamesCategoryDirection
import ca.six.hojat.gamehub.feature.category.GamesCategoryScreen
import ca.six.hojat.gamehub.feature.discovery.GamesDiscoveryDirection
import ca.six.hojat.gamehub.feature.discovery.GamesDiscoveryScreen
import ca.six.hojat.gamehub.feature.image.viewer.ImageViewerDirection
import ca.six.hojat.gamehub.feature.image.viewer.ImageViewerScreen
import ca.six.hojat.gamehub.feature.info.presentation.GameInfoDirection
import ca.six.hojat.gamehub.feature.info.presentation.GameInfoScreen
import ca.six.hojat.gamehub.feature.likes.presentation.LikedGamesDirection
import ca.six.hojat.gamehub.feature.likes.presentation.LikedGamesScreen
import ca.six.hojat.gamehub.feature.news.presentation.GamingNewsScreen
import ca.six.hojat.gamehub.feature.search.presentation.GamesSearchDirection
import ca.six.hojat.gamehub.feature.search.presentation.GamesSearchScreen
import ca.six.hojat.gamehub.feature.settings.presentation.SettingsScreen
import kotlin.reflect.KClass
import kotlin.reflect.KType

@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = START_SCREEN.routeClass,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        gamesDiscoveryScreen(
            navController = navController,
            modifier = modifier,
        )
        likedGamesScreen(
            navController = navController,
            modifier = modifier,
        )
        gamingNewsScreen(modifier = modifier)
        settingsScreen(modifier = modifier)
        gamesSearchScreen(navController = navController)
        gamesCategoryScreen(navController = navController)
        gameInfoScreen(navController = navController)
        imageViewerScreen(navController = navController)
    }
}

private fun NavGraphBuilder.gamesDiscoveryScreen(
    navController: NavHostController,
    modifier: Modifier,
) {
    composable(
        route = Screen.DiscoverScreen.routeClass,
        enterTransition = { null },
        exitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.SearchScreen -> OvershootScaling.exit()
                Screen.CategoryScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.exit()

                else -> null
            }
        },
        popEnterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.SearchScreen -> OvershootScaling.popEnter()
                Screen.CategoryScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.popEnter()

                else -> null
            }
        },
        popExitTransition = { null },
    ) {
        GamesDiscoveryScreen(modifier) { direction ->
            when (direction) {
                is GamesDiscoveryDirection.Search -> {
                    navController.navigate(Screen.SearchScreen.route)
                }

                is GamesDiscoveryDirection.Category -> {
                    navController.navigate(Screen.CategoryScreen.createRoute(category = direction.category))
                }

                is GamesDiscoveryDirection.Info -> {
                    navController.navigate(Screen.GameInfoScreen.createRoute(gameId = direction.gameId))
                }
            }
        }
    }
}

private fun NavGraphBuilder.likedGamesScreen(
    navController: NavHostController,
    modifier: Modifier,
) {
    composable(
        route = Screen.LikesScreen.routeClass,
        enterTransition = { null },
        exitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.SearchScreen -> OvershootScaling.exit()
                Screen.GameInfoScreen -> HorizontalSliding.exit()
                else -> null
            }
        },
        popEnterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.SearchScreen -> OvershootScaling.popEnter()
                Screen.GameInfoScreen -> HorizontalSliding.popEnter()
                else -> null
            }
        },
        popExitTransition = { null },
    ) {
        LikedGamesScreen(modifier) { direction ->
            when (direction) {
                is LikedGamesDirection.Search -> {
                    navController.navigate(Screen.SearchScreen.route)
                }

                is LikedGamesDirection.Info -> {
                    navController.navigate(Screen.GameInfoScreen.createRoute(gameId = direction.gameId))
                }
            }
        }
    }
}

private fun NavGraphBuilder.gamingNewsScreen(modifier: Modifier) {
    composable(route = Screen.NewsScreen.routeClass) {
        GamingNewsScreen(modifier)
    }
}

private fun NavGraphBuilder.settingsScreen(modifier: Modifier) {
    composable(route = Screen.SettingsScreen.routeClass) {
        SettingsScreen(modifier)
    }
}

private fun NavGraphBuilder.gamesSearchScreen(navController: NavHostController) {
    composable(
        route = Screen.SearchScreen.routeClass,
        enterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.NewsScreen,
                Screen.DiscoverScreen,
                Screen.LikesScreen,
                    -> OvershootScaling.enter()

                else -> null
            }
        },
        exitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.exit()
                else -> null
            }
        },
        popEnterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.popEnter()
                else -> null
            }
        },
        popExitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.NewsScreen,
                Screen.DiscoverScreen,
                Screen.LikesScreen,
                    -> OvershootScaling.popExit()

                else -> null
            }
        },
    ) {
        GamesSearchScreen { direction ->
            when (direction) {
                is GamesSearchDirection.Info -> {
                    navController.navigate(Screen.GameInfoScreen.createRoute(gameId = direction.gameId))
                }

                is GamesSearchDirection.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

private fun NavGraphBuilder.gamesCategoryScreen(navController: NavHostController) {
    composable(
        route = Screen.CategoryScreen.routeClass,
        enterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.DiscoverScreen -> HorizontalSliding.enter()
                else -> null
            }
        },
        exitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.exit()
                else -> null
            }
        },
        popEnterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.popEnter()
                else -> null
            }
        },
        popExitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.DiscoverScreen -> HorizontalSliding.popExit()
                else -> null
            }
        },
    ) { entry ->
        GamesCategoryScreen(route = entry.toRoute()) { direction ->
            when (direction) {
                is GamesCategoryDirection.Info -> {
                    navController.navigate(Screen.GameInfoScreen.createRoute(gameId = direction.gameId))
                }

                is GamesCategoryDirection.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

private fun NavGraphBuilder.gameInfoScreen(navController: NavHostController) {
    composable(
        route = Screen.GameInfoScreen.routeClass,
        enterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.DiscoverScreen,
                Screen.LikesScreen,
                Screen.SearchScreen,
                Screen.CategoryScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.enter()

                else -> null
            }
        },
        exitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.ImageViewerScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.exit()

                else -> null
            }
        },
        popEnterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.ImageViewerScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.popEnter()

                else -> null
            }
        },
        popExitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.DiscoverScreen,
                Screen.LikesScreen,
                Screen.SearchScreen,
                Screen.CategoryScreen,
                Screen.GameInfoScreen,
                    -> HorizontalSliding.popExit()

                else -> null
            }
        },
    ) { entry ->
        GameInfoScreen(route = entry.toRoute()) { direction ->
            when (direction) {
                is GameInfoDirection.ImageViewer -> {
                    navController.navigate(
                        Screen.ImageViewerScreen.createRoute(
                            imageUrls = direction.imageUrls,
                            title = direction.title,
                            initialPosition = direction.initialPosition,
                        ),
                    )
                }

                is GameInfoDirection.Info -> {
                    navController.navigate(Screen.GameInfoScreen.createRoute(gameId = direction.gameId))
                }

                is GameInfoDirection.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }
}

private fun NavGraphBuilder.imageViewerScreen(navController: NavHostController) {
    composable(
        route = Screen.ImageViewerScreen.routeClass,
        enterTransition = {
            when (Screen.Companion.forRoute(initialState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.enter()
                else -> null
            }
        },
        exitTransition = { null },
        popEnterTransition = { null },
        popExitTransition = {
            when (Screen.Companion.forRoute(targetState.destination.requireRoute())) {
                Screen.GameInfoScreen -> HorizontalSliding.popExit()
                else -> null
            }
        },
    ) { entry ->
        ImageViewerScreen(route = entry.toRoute()) { direction ->
            when (direction) {
                is ImageViewerDirection.Back -> navController.popBackStack()
            }
        }
    }
}

@Suppress("LongParameterList")
private fun NavGraphBuilder.composable(
    route: KClass<*>,
    typeMap: Map<KType, NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = null,
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = null,
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        enterTransition,
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        exitTransition,
    sizeTransform: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? = null,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    destination(
        ComposeNavigatorDestinationBuilder(
            provider[ComposeNavigator::class],
            route,
            typeMap,
            content,
        )
            .apply {
                deepLinks.forEach { deepLink -> deepLink(deepLink) }
                this.enterTransition = enterTransition
                this.exitTransition = exitTransition
                this.popEnterTransition = popEnterTransition
                this.popExitTransition = popExitTransition
                this.sizeTransform = sizeTransform
            },
    )
}
