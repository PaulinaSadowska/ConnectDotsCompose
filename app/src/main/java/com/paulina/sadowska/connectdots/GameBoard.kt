package com.paulina.sadowska.connectdots

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun GameBoard(
        modifier: Modifier = Modifier,
        boardController: BoardController,
) {
    val boardSize = 300.dp
    val radius = 12.dp
    val dotColor = MaterialTheme.colorScheme.primary
    val circlesController = with(LocalDensity.current) {
         CirclesController(
                numOfElements = 4,
                boardSize = boardSize.toPx(),
                radiusPx = radius.toPx().roundToInt()
        )
    }

    Canvas(
            modifier = modifier
                    .size(size = boardSize)
                    .pointerInput(Unit) {
                        detectDragGestures(
                                onDragStart = { newPoint ->
                                    val circle = circlesController.findCircleInPoint(newPoint)
                                    circle?.let{
                                        boardController.insertNewPath(circle)
                                    }
                                },
                                onDragCancel = {
                                    boardController.removeLastPath()
                                },
                                onDragEnd = {
                                    boardController.removeLastPath()
                                }
                        ) { change, _ ->
                            val newPoint = change.position
                            val circle = circlesController.findCircleInPoint(newPoint)
                            circle?.let{
                                boardController.updateLatestPath(circle)
                                boardController.insertNewPath(circle)
                            } ?: run {
                                boardController.updateLatestPath(newPoint)
                            }
                        }
                        detectTapGestures {
                            boardController.onTap()
                        }
                    }
    ) {
        circlesController.positions.forEach {
            drawCircle(
                    color = dotColor,
                    radius = radius.toPx(),
                    center = it
            )
        }

        boardController._paths.forEach {
            drawLine(
                    color = dotColor,
                    start = it.start,
                    end = it.end,
                    strokeWidth = radius.toPx() / 2
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameBoardPreview() {
    GameBoard(boardController = BoardController())
}

@Composable
fun rememberDrawController(): BoardController {
    return remember { BoardController() }
}
