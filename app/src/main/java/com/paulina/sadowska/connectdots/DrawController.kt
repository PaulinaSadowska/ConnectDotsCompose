package com.paulina.sadowska.connectdots

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.CoroutineScope

class DrawController {

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

}

data class Path(
        val start: Offset,
        val end: Offset,
)