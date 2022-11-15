package com.paulina.sadowska.connectdots

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GameBoard(
        modifier: Modifier = Modifier,
        drawController: DrawController,
) {
    val radius = 8.dp
    val boardSize = 300.dp
    val numOfElements = 8
    val multiplier = 1.0f / (numOfElements + 1)
    val dotColor = MaterialTheme.colorScheme.primary
    Canvas(
            modifier = modifier
                    .size(size = boardSize)
                    .pointerInput(Unit) {
                        detectDragGestures(
                                onDragStart = { offset ->
                                    drawController.insertNewPath(offset)
                                }
                        ) { change, _ ->
                            val newPoint = change.position
                            drawController.updateLatestPath(newPoint)
                        }
                        detectTapGestures {
                            drawController.onTap()
                        }
                    }
    ) {
        val boardWidth = this.size.width
        val boardHeight = this.size.height

        for (i in 1..numOfElements) {
            val offsetX = boardWidth * i * multiplier
            for (j in 1..numOfElements) {
                val offsetY = boardHeight * j * multiplier
                drawCircle(
                        color = dotColor,
                        radius = radius.toPx(),
                        center = Offset(offsetX, offsetY)
                )
            }
        }

        drawController._paths.forEach {
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
    GameBoard(drawController = DrawController())
}

@Composable
fun rememberDrawController(): DrawController {
    return remember { DrawController() }
}
