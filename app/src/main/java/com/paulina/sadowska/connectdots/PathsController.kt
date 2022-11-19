package com.paulina.sadowska.connectdots

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset

class PathsController {

    internal val paths = SnapshotStateList<Path>()

    fun insertNewPath(offset: Offset) {
        paths.add(Path(offset, offset))
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

    fun createNewPathSection(circle: Offset): Boolean {
        return if (paths.isNotEmpty()) {
            if (circle != paths[paths.lastIndex].end) {
                updateLatestPath(circle)
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
)

class CirclesController(
        numOfElements: Int,
        boardSize: Float,
        private val radiusPx: Int
) {
    private val multiplier = 1.0f / (numOfElements + 1)
    val positions = (1..numOfElements).flatMap { i ->
        val offsetX = boardSize * i * multiplier
        (1..numOfElements).map { j ->
            val offsetY = boardSize * j * multiplier
            Offset(offsetX, offsetY)
        }
    }

    fun findCircleInPoint(point: Offset): Offset? {
        return positions.find { circlePosition ->
            point.x > (circlePosition.x - radiusPx) && point.x < (circlePosition.x + radiusPx) &&
                    point.y > (circlePosition.y - radiusPx) && point.y < (circlePosition.y + radiusPx)
        }
    }
}