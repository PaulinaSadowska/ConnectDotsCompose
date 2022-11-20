package com.paulina.sadowska.connectdots

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun RippleDotsBoard(
        modifier: Modifier = Modifier,
        gameController: GameController,
        boardSize: Dp,
        radius: Dp,
) {

    val dotColor = MaterialTheme.colorScheme.primary
    val radiusPx = with(LocalDensity.current) {
        radius.toPx()
    }

    var size by remember {
        mutableStateOf(Animatable(radiusPx))
    }

    var alpha by remember {
        mutableStateOf(Animatable(0f))
    }

    LaunchedEffect(size, alpha) {
        launch {
            size.animateTo(targetValue = 100f, animationSpec = tween(1000))
        }
        launch {
            alpha.animateTo(targetValue = 0f, animationSpec = tween(1000))
        }
    }

    gameController.onDotConnected {
        size = Animatable(radiusPx)
        alpha = Animatable(1f)
    }

    Canvas(
            modifier = modifier
                    .size(size = boardSize)

    ) {
        gameController.circlePositions.forEachIndexed { index, offset ->
            drawCircle(
                    color = dotColor,
                    alpha = alpha.value,
                    radius = size.value,
                    center = offset
            )
        }

    }
}

