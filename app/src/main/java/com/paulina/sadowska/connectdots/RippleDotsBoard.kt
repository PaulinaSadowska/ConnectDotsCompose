package com.paulina.sadowska.connectdots

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch

@Composable
fun RippleDotsBoard(
        modifier: Modifier = Modifier,
        gameController: GameController,
        boardSize: Dp,
        radius: Dp,
) {
    val radiusPx = with(LocalDensity.current) {
        radius.toPx()
    }

    val size = remember {
        mutableStateListOf(*(List(20) { Animatable(radiusPx) }.toTypedArray()))
    }

    val alpha = remember {
        mutableStateListOf(*(List(20) { Animatable(0f) }.toTypedArray()))
    }

    gameController.onDotConnected { index, scope ->
        with(scope) {
            launch {
                size[index] = Animatable(radiusPx)
                size[index].animateTo(targetValue = 100f, animationSpec = tween(1000))
            }
            launch {
                alpha[index] = Animatable(1f)
                alpha[index].animateTo(targetValue = 0f, animationSpec = tween(1000))
            }
        }
    }

    Canvas(modifier = modifier.size(size = boardSize)) {
        gameController.circles.forEachIndexed { index, circle ->
            drawCircle(
                    color = circle.color,
                    alpha = alpha[index].value,
                    radius = size[index].value,
                    center = circle.position
            )
        }
    }
}

