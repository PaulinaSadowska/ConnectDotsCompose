package com.paulina.sadowska.connectdots

import androidx.compose.ui.geometry.Offset

class GameController(
        private val pathsController: PathsController,
        private val circlesController: CirclesController,
) {
    val paths = pathsController.paths
    val circlePositions = circlesController.positions
    var onDotConnected: ((Int) -> Unit)? = null

    fun onDragStart(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { (circle, index) ->
            pathsController.insertNewPath(circle)
            onDotConnected?.let { it(index) }
        }
    }

    fun onDragEnd() {
        pathsController.removeLastPath()
    }

    fun onDragChange(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { (circle, index) ->
            if (pathsController.createNewPathSection(circle)) {
                onDotConnected?.let { it(index) }
            }
        } ?: run {
            pathsController.updateLatestPath(point)
        }
    }

    fun onDotConnected(callback: (Int) -> Unit) {
        onDotConnected = callback
    }
}