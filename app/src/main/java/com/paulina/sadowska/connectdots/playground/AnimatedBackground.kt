package com.paulina.sadowska.connectdots.playground


import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBackground() {
    var animateToGreen by remember { mutableStateOf(false) }
    Column {
        Button(onClick = {
            animateToGreen = animateToGreen.not()
        }) {
            Text("toggle ok = $animateToGreen")
        }
        AnimatedBox(ok = animateToGreen)
    }

}

@Composable
fun AnimatedBox(ok: Boolean) {
    val color = remember { androidx.compose.animation.Animatable(Color.Gray) }
    LaunchedEffect(ok) {
        color.animateTo(if (ok) Color.Green else Color.Red, animationSpec = tween(2000))
    }
    Box(Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color.value))
}