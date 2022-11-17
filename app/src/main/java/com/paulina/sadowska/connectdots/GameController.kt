package com.paulina.sadowska.connectdots

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset

class GameController(
        private val pathsController: PathsController,
        private val circlesController: CirclesController,
) {
    val paths = pathsController.paths
    val circlePositions = circlesController.positions

    internal val _ripple = SnapshotStateList<Offset>()

    fun onDragStart(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { circle ->
            pathsController.insertNewPath(circle)
        }
    }

    fun onDragEnd() {
        pathsController.removeLastPath()
    }

    fun onDragChange(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { circle ->
            pathsController.updateLatestPath(circle)
            pathsController.insertNewPath(circle)
        } ?: run {
            pathsController.updateLatestPath(point)
        }
    }

    fun onTap(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { circle ->
            _ripple.add(circle)
        }
    }

}