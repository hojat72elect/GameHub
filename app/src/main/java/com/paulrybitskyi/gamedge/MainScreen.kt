
package com.paulrybitskyi.gamedge

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
internal fun MainScreen() {
    val navController = rememberNavController()
    val currentScreen by navController.currentScreenAsState()

    Scaffold(
        bottomBar = {
            BottomBar(
                navController = navController,
                currentScreen = currentScreen,
            )
        },
        content = { paddingValues ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(paddingValues),
            )
        },
    )
}
