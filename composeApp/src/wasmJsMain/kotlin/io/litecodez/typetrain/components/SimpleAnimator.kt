package io.litecodez.typetrain.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import io.litecodez.typetrain.objects.AnimationStyle

import kotlinx.coroutines.delay



/**
 * Composable function that provides simple animations based on the specified style.
 *
 * @param style The animation style to apply (default is AnimationStyle.RIGHT).
 * @param modifier The modifier for the animated content (default is Modifier).
 * @param task The task to be executed after the animation (default is an empty lambda).
 * @param animationCounter The counter for the animation (default is 0).
 * @param content The content to animate.
 */
@Composable
fun SimpleAnimator(
    style: AnimationStyle = AnimationStyle.RIGHT,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    task: (Int) -> Unit = {},
    animationCounter: Int = 0,
    duration: Int = 200,
    content: @Composable () -> Unit
) {

    @Composable
    fun Right() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(animationSpec = tween(durationMillis = duration)),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    @Composable
    fun Left() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(animationSpec = tween(durationMillis = duration)),
            exit = slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    @Composable
    fun ScaleInTop() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = scaleIn(
                initialScale = 0f,
                transformOrigin = TransformOrigin(0.5f, 0f),
                animationSpec = tween(durationMillis = duration)
            ),
            exit = scaleOut(
                targetScale = 0f,
                transformOrigin = TransformOrigin(0.5f, 0f),
                animationSpec = tween(durationMillis = duration)
            )
        ) {
            content()
        }
    }

    @Composable
    fun ScaleInLeft() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = scaleIn(
                initialScale = 0f,
                transformOrigin = TransformOrigin(0f, 0f),
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(animationSpec = tween(durationMillis = duration)),
            exit = scaleOut(
                targetScale = 0f,
                transformOrigin = TransformOrigin(0f, 0f),
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    @Composable
    fun Up() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { 40 },
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(durationMillis = duration)
            ),
            exit = slideOutVertically(
                targetOffsetY = { 40 },
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    @Composable
    fun Down() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(
                initialAlpha = 0.3f,
                animationSpec = tween(durationMillis = duration)
            ),
            exit = slideOutVertically(
                targetOffsetY = { -40 },
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    @Composable
    fun Transition(color: Color = Color.White, speed: Int = 15) {
        var visible by remember { mutableStateOf(false) }
        var setAlpha by remember { mutableFloatStateOf(1.0f) }
        var isAlphaSet by remember { mutableStateOf(false) }
        val colorList = remember { mutableStateListOf<Color>() }

        LaunchedEffect(isVisible) {
            visible = isVisible
        }

        if (!isAlphaSet) {
            var counter = setAlpha
            while (counter > 0) {
                colorList.add(color.copy(alpha = counter))
                counter -= 0.05f
            }
            colorList.add(color.copy(alpha = 0.0f))
            isAlphaSet = true
        }

        var transitionColor by remember { mutableStateOf(color.copy(alpha = setAlpha)) }

        LaunchedEffect(visible) {
            if (visible) {
                // Entry animation
                for (i in colorList.reversed()) {
                    transitionColor = i
                    delay(speed.toLong())
                }
                setAlpha = 0.0f
            } else {
                // Exit animation
                for (i in colorList) {
                    transitionColor = i
                    delay(speed.toLong())
                }
                setAlpha = 1.0f
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(transitionColor)
        ) {
            if (setAlpha <= 0f) {
                content()
            }
        }
    }

    @Composable
    fun ScaleInCenter() {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(isVisible) {
            visible = isVisible
        }
        AnimatedVisibility(
            modifier = modifier,
            visible = visible,
            enter = scaleIn(
                initialScale = 0f,
                transformOrigin = TransformOrigin.Center,
                animationSpec = tween(durationMillis = duration)
            ) + fadeIn(animationSpec = tween(durationMillis = duration)),
            exit = scaleOut(
                targetScale = 0f,
                transformOrigin = TransformOrigin.Center,
                animationSpec = tween(durationMillis = duration)
            ) + fadeOut(animationSpec = tween(durationMillis = duration))
        ) {
            content()
        }
    }

    when (style) {
        AnimationStyle.RIGHT -> {
            Right()
            task(animationCounter)
        }
        AnimationStyle.LEFT -> {
            Left()
            task(animationCounter)
        }
        AnimationStyle.SCALE_IN_TOP -> {
            ScaleInTop()
            task(animationCounter)
        }
        AnimationStyle.SCALE_IN_LEFT -> {
            ScaleInLeft()
            task(animationCounter)
        }
        AnimationStyle.UP -> {
            Up()
            task(animationCounter)
        }
        AnimationStyle.DOWN -> {
            Down()
            task(animationCounter)
        }
        AnimationStyle.TRANSITION -> {
            Transition()
            task(animationCounter)
        }
        AnimationStyle.SCALE_IN_CENTER -> {
            ScaleInCenter()
            task(animationCounter)
        }
        AnimationStyle.NONE -> {
            content()
            task(animationCounter)
        }
    }
}