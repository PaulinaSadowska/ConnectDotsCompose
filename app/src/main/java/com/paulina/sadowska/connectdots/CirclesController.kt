package com.paulina.sadowska.connectdots

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color

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