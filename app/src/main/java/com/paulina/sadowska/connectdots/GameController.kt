package com.paulina.sadowska.connectdots

import androidx.compose.ui.geometry.Offset

class GameController(
        private val pathsController: PathsController,
        private val circlesController: CirclesController,
) {
    val paths = pathsController.paths
    val circlePositions = circlesController.positions
    var onDotConnected: ((Offset) -> Unit)? = null

    fun onDragStart(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { circle ->
            pathsController.insertNewPath(circle)
            onDotConnected?.let { it(circle) }
        }
    }

    fun onDragEnd() {
        pathsController.removeLastPath()
    }

    fun onDragChange(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { circle ->
            if (pathsController.createNewPathSection(circle)) {
                onDotConnected?.let { it(circle) }
            }
        } ?: run {
            pathsController.updateLatestPath(point)
        }
    }

    fun onDotConnected(callback: (Offset) -> Unit) {
        onDotConnected = callback
    }
}