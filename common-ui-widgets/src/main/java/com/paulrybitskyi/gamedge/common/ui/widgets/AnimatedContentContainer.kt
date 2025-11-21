
package com.paulrybitskyi.gamedge.common.ui.widgets

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

private const val AnimationDuration = 500

@Composable
fun AnimatedContentContainer(
    finiteUiState: FiniteUiState,
    modifier: Modifier = Modifier,
    exitTransition: ExitTransition = fadeOut(animationSpec = tween(AnimationDuration)),
    enterTransition: EnterTransition = fadeIn(animationSpec = tween(AnimationDuration)),
    content: @Composable BoxScope.(FiniteUiState) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = finiteUiState,
            transitionSpec = {
                val finalExitTransition = when (initialState) {
                    FiniteUiState.Empty,
                    FiniteUiState.Loading,
                    -> ExitTransition.None
                    FiniteUiState.Success -> exitTransition
                }
                val finalEnterTransition = when (targetState) {
                    FiniteUiState.Loading -> EnterTransition.None
                    FiniteUiState.Empty,
                    FiniteUiState.Success,
                    -> enterTransition
                }

                finalEnterTransition togetherWith finalExitTransition
            },
            label = "AnimatedContentContainer",
        ) { targetUiState ->
            Box(modifier = Modifier.fillMaxSize()) {
                content(targetUiState)
            }
        }
    }
}

enum class FiniteUiState {
    Empty,
    Loading,
    Success,
}
