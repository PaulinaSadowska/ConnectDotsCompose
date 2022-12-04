package com.paulina.sadowska.connectdots

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

class PathsController {

    internal val paths = SnapshotStateList<Path>()

    fun insertNewPath(circle: Circle) {
        paths.add(Path(circle.position, circle.position, circle.color, false))
    }

    fun updateLatestPath(newPoint: Offset) {
        if (paths.isNotEmpty() && !paths[paths.lastIndex].finished) {
            val lastPath = paths.removeAt(paths.lastIndex)
            paths.add(lastPath.copy(end = newPoint))
        }
    }

    fun removeLastPath() {
        if (paths.isNotEmpty() && !paths[paths.lastIndex].finished) {
            paths.removeAt(paths.lastIndex)
            val lastPath = paths.removeAt(paths.lastIndex)
            paths.add(lastPath.copy(finished = true))
        }
    }

    fun createNewPathSection(circle: Circle): Boolean {
        return paths.getOrNull(paths.lastIndex)?.let { lastPath ->
            if (circle.position != lastPath.end && circle.color == lastPath.color) {
                updateLatestPath(circle.position)
                insertNewPath(circle)
                true
            } else {
                false
            }
        } ?: false
    }
}

data class Path(
        val start: Offset,
        val end: Offset,
        val color: Color,
        val finished: Boolean
)
