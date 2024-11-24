package io.litecodez.typetrain.components

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.random.Random

@Composable
fun BoxWithRandomElement(
    modifier: Modifier = Modifier,
    padding: Int = 0,
    content: @Composable (
        position: State<IntOffset>,
        updatePosition: () -> Unit?
    ) -> Unit
) {
    var containerSize by remember { mutableStateOf<IntSize?>(null) }
    val density = LocalDensity.current

    // Remember the random position state
    var randomPosition by remember { mutableStateOf(IntOffset.Zero) }

    // Function to generate new random position
    val updatePosition = {
        containerSize?.let { size ->
//            val width = with(density) { size.width.toDp().value.toInt() }
//            val height = with(density) { size.height.toDp().value.toInt() }

            val width = size.width
            val height = size.height

            randomPosition = IntOffset(
                x = Random.nextInt(padding, width - padding),
                y = Random.nextInt(padding, height - padding)
            )
        }
    }

    // Position state that can be observed by children
    val positionState = remember { derivedStateOf { randomPosition } }

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                containerSize = coordinates.size
                // Generate initial position if we don't have one yet
                if (randomPosition == IntOffset.Zero) {
                    updatePosition()
                }
            }
    ) {
        content(positionState, updatePosition)
    }
}