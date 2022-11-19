package com.paulina.sadowska.connectdots.playground


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch

@Composable
fun RippleOnClick() {

    var size by remember {
        mutableStateOf(Animatable(0f))
    }

    var alpha by remember {
        mutableStateOf(Animatable(1f))
    }

    var offset by remember {
        mutableStateOf<Offset?>(null)
    }

    LaunchedEffect(size, alpha) {
        launch {
            size.animateTo(targetValue = 100f)
        }
        launch {
            alpha.animateTo(targetValue = 0f)
        }
    }
    Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures {
                    size = Animatable(0f)
                    alpha = Animatable(1f)
                    offset = it
                }
            }, onDraw = {
        offset?.let {
            drawCircle(
                    color = Color.Blue,
                    alpha = alpha.value,
                    radius = size.value,
                    center = it
            )
        }
    })
}