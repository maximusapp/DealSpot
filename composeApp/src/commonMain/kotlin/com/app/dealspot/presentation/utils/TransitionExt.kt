package com.app.dealspot.presentation.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally

private const val DEFAULT_TRANSITION_DURATION = 200

// --- Forward Navigation ---
fun AnimatedContentTransitionScope<*>.defaultEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = LinearEasing)) +
            slideIntoContainer(
                animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
}

fun AnimatedContentTransitionScope<*>.defaultExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = LinearEasing)) +
            slideOutOfContainer(
                animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
}

// --- Back Navigation ---
fun AnimatedContentTransitionScope<*>.defaultPopEnterTransition(): EnterTransition {
    return fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = LinearEasing)) +
            slideIntoContainer(
                animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = EaseIn),
                towards = AnimatedContentTransitionScope.SlideDirection.End
            )
}

fun AnimatedContentTransitionScope<*>.defaultPopExitTransition(): ExitTransition {
    return fadeOut(animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = LinearEasing)) +
            slideOutOfContainer(
                animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = EaseOut),
                towards = AnimatedContentTransitionScope.SlideDirection.Start
            )
}

fun AnimatedContentTransitionScope<*>.slideInHorizontally(): EnterTransition {
    return fadeIn(animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = LinearEasing)) +
            slideInHorizontally(
                animationSpec = tween(DEFAULT_TRANSITION_DURATION, easing = EaseIn),
                initialOffsetX = { fullWidth -> fullWidth }
            )
}