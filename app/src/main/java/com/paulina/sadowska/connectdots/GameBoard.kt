package com.paulina.sadowska.connectdots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp

@Composable
fun GameBoard(
        modifier: Modifier = Modifier,
        gameController: GameController,
        boardSize: Dp,
        radius: Dp,
) {

    val dotColor = MaterialTheme.colorScheme.primary

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
                        detectTapGestures {
                            gameController.onTap(it)
                        }
                    }
    ) {
        gameController.circlePositions.forEach {
            drawCircle(
                    color = dotColor,
                    radius = radius.toPx(),
                    center = it
            )
        }

        gameController.paths.forEach {
            drawLine(
                    color = dotColor,
                    start = it.start,
                    end = it.end,
                    strokeWidth = radius.toPx() / 2
            )
        }
    }
}

