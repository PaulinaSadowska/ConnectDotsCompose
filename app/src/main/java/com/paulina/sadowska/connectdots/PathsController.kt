package com.paulina.sadowska.connectdots

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class PathsController {

    internal val paths = SnapshotStateList<Path>()

    fun insertNewPath(circle: Circle) {
        paths.add(Path(circle.position, circle.position, circle.color))
    }

    fun updateLatestPath(newPoint: Offset) {
        if (paths.isNotEmpty()) {
            val lastPath = paths.removeAt(paths.lastIndex)
            paths.add(lastPath.copy(end = newPoint))
        }
    }

    fun removeLastPath() {
        if (paths.isNotEmpty()) {
            paths.removeAt(paths.lastIndex)
        }
    }

    fun createNewPathSection(circle: Circle): Boolean {
        return if (paths.isNotEmpty()) {
            val lastPath = paths[paths.lastIndex]
            if (circle.position != lastPath.end && circle.color == lastPath.color) {
                updateLatestPath(circle.position)
                insertNewPath(circle)
                true
            } else {
                false
            }
        } else {
            false
        }
    }

}

data class Path(
        val start: Offset,
        val end: Offset,
        val color: Color
)

class CirclesController(
        numOfElements: Int,
        boardSize: Float,
        private val radiusPx: Int,
        private val colors: List<Color>
) {
    private val multiplier = 1.0f / (numOfElements + 1)
    val circles = (1..numOfElements).flatMap { i ->
        val offsetX = boardSize * i * multiplier
        (1..numOfElements).map { j ->
            val offsetY = boardSize * j * multiplier
            Circle(Offset(offsetX, offsetY), colors.random())
        }
    }

    fun findCircleInPoint(point: Offset): Pair<Circle, Int>? {
        val circle = circles.find { circle ->
            val circlePosition = circle.position
            point.x > (circlePosition.x - radiusPx) && point.x < (circlePosition.x + radiusPx) &&
                    point.y > (circlePosition.y - radiusPx) && point.y < (circlePosition.y + radiusPx)
        }
        return circle?.let { Pair(circle, circles.indexOf(circle)) }
    }
}

data class Circle(
        val position: Offset,
        val color: Color
)