package com.paulina.sadowska.connectdots

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.paulina.sadowska.connectdots.playground.RippleOnClick
import com.paulina.sadowska.connectdots.ui.theme.ConnectDotsTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectDotsTheme {
                Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                ) {
                    Box {
                        val boardSize = 300.dp
                        val radius = 12.dp
                        val gameController = rememberGameController(boardSize, radius)
                        //RippleOnClick()
                        //AnimatedBackground()
                        RippleDotsBoard(
                                modifier = Modifier.align(Alignment.Center),
                                gameController = gameController,
                                boardSize = boardSize,
                                radius = radius,
                        )
                        GameBoard(
                                modifier = Modifier.align(Alignment.Center),
                                gameController = gameController,
                                boardSize = boardSize,
                                radius = radius,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberGameController(
        boardSize: Dp,
        radius: Dp
): GameController {
    val circlesController = with(LocalDensity.current) {
        CirclesController(
                numOfElements = 4,
                boardSize = boardSize.toPx(),
                radiusPx = radius.toPx().roundToInt()
        )
    }
    return remember {
        GameController(
                pathsController = PathsController(),
                circlesController = circlesController
        )
    }
}