package com.paulina.sadowska.connectdots

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun GameBoard(
        modifier: Modifier = Modifier,
        gameController: GameController,
        boardSize: Dp,
        radius: Dp,
) {
    Canvas(
            modifier = modifier
                    .size(size = boardSize)
                    .pointerInput(Unit) {
                        detectDragGestures(
                                onDragStart = {
                                    gameController.onDragStart(it)
                                },
                                onDragCancel = {
                                    gameController.onDragEnd()
                                },
                                onDragEnd = {
                                    gameController.onDragEnd()
                                }
                        ) { change, _ ->
                            gameController.onDragChange(change.position)
                        }
                    }
    ) {
        gameController.circles.forEach {
            drawCircle(
                    color = it.color,
                    radius = radius.toPx(),
                    center = it.position
            )
        }

        gameController.paths.forEach {
            drawLine(
                    color = it.color,
                    start = it.start,
                    end = it.end,
                    strokeWidth = radius.toPx() / 2
            )
        }
    }
}

