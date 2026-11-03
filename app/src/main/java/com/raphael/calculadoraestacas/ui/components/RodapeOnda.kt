package com.raphael.calculadoraestacas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

@Composable
fun RodapeOnda(modifier: Modifier = Modifier) {
    val navBarHeight = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    val totalHeight = 30.dp + navBarHeight

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(totalHeight)
    ) {
        val width = size.width
        val height = size.height

        // Onda azul preenchendo toda a base esquerda e central
        val pathAzul = Path().apply {
            moveTo(0f, height * 0.12f)
            cubicTo(
                width * 0.28f, 0f,
                width * 0.55f, height * 0.35f,
                width, height * 0.45f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        drawPath(pathAzul, color = Color(0xFF0055AC))

        // Onda verde sobreposta à direita preenchendo do centro até a direita
        val pathVerde = Path().apply {
            moveTo(width * 0.32f, height)
            cubicTo(
                width * 0.52f, height * 0.38f,
                width * 0.78f, height * 0.18f,
                width, height * 0.08f
            )
            lineTo(width, height)
            close()
        }
        drawPath(pathVerde, color = Color(0xFF16A34A))
    }
}
