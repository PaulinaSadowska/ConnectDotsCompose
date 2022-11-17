package com.paulina.sadowska.connectdots

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset

class BoardController {

    internal val _paths = SnapshotStateList<Path>()


    fun insertNewPath(offset: Offset) {
        _paths.add(Path(offset, offset))
    }

    fun updateLatestPath(newPoint: Offset) {
        if (_paths.isNotEmpty()) {
            val lastPath = _paths.removeAt(_paths.lastIndex)
            _paths.add(lastPath.copy(end = newPoint))
        }
    }

    fun onTap() {
        //TODO("Not yet implemented")
    }

    fun removeLastPath() {
        _paths.removeAt(_paths.lastIndex)
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