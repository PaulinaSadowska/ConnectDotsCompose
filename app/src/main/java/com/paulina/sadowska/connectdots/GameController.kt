package com.paulina.sadowska.connectdots

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope

class GameController(
        private val pathsController: PathsController,
        private val circlesController: CirclesController,
        private val coroutineScope: CoroutineScope
) {
    val paths = pathsController.paths
    val circles = circlesController.circles
    private var onDotConnected: ((Int, CoroutineScope) -> Unit)? = null

    fun onDragStart(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { (circle, index) ->
            pathsController.insertNewPath(circle)
            onDotConnected?.let { it(index, coroutineScope) }
        }
    }

    fun onDragEnd() {
        pathsController.removeLastPath()
    }

    fun onDragChange(point: Offset) {
        circlesController.findCircleInPoint(point)?.let { (circle, index) ->
            if (pathsController.createNewPathSection(circle)) {
                onDotConnected?.let { it(index, coroutineScope) }
            }
        } ?: run {
            pathsController.updateLatestPath(point)
        }
    }

    fun onDotConnected(callback: (Int, CoroutineScope) -> Unit) {
        onDotConnected = callback
    }
}