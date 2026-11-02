package com.raphael.calculadoraestacas.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val HeaderWaveShape = GenericShape { size, _ ->
    moveTo(0f, 0f)
    lineTo(size.width, 0f)
    lineTo(size.width, size.height * 0.78f)
    // Curva orgânica suave em S sem quinas ou cortes retos
    cubicTo(
        size.width * 0.75f, size.height * 0.72f,
        size.width * 0.42f, size.height * 1.04f,
        0f, size.height * 0.88f
    )
    close()
}

@Composable
fun HeaderApp(
    onAbrirConfiguracoes: () -> Unit,
    modifier: Modifier = Modifier
) {
    val topSafeInset = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
    val headerHeight = 175.dp + topSafeInset

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(headerHeight)
    ) {
        // Fundo com degradê azul e recorte em curva suave
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(HeaderWaveShape)
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF063970),
                            Color(0xFF0055AC),
                            Color(0xFF0D74D6)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 500f)
                    )
                )
        ) {
            // Ondas decorativas translúcidas sutis no fundo do cabeçalho
            Canvas(modifier = Modifier.fillMaxSize()) {
                val w = size.width
                val h = size.height

                val ondaSuave1 = Path().apply {
                    moveTo(w * 0.35f, 0f)
                    cubicTo(
                        w * 0.55f, h * 0.45f,
                        w * 0.78f, h * 0.25f,
                        w, h * 0.65f
                    )
                    lineTo(w, 0f)
                    close()
                }
                drawPath(ondaSuave1, color = Color.White.copy(alpha = 0.07f))

                val ondaSuave2 = Path().apply {
                    moveTo(0f, h * 0.65f)
                    cubicTo(
                        w * 0.35f, h * 0.45f,
                        w * 0.65f, h * 0.95f,
                        w, h * 0.50f
                    )
                    lineTo(w, h)
                    lineTo(0f, h)
                    close()
                }
                drawPath(ondaSuave2, color = Color.White.copy(alpha = 0.05f))
            }

            // Conteúdo principal do cabeçalho (Ícone + Título alinhados e legíveis)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, top = topSafeInset + 22.dp, end = 22.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone estilizado com fundo translúcido (efeito glass)
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                        .border(
                            1.5.dp,
                            Color.White.copy(alpha = 0.35f),
                            RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Straighten,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Calculadora de\nestacas",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    lineHeight = 28.sp
                )
            }
        }

        // Botão flutuante de configurações encaixado suavemente na curva direita
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp)
                .offset(y = (-6).dp)
                .size(58.dp)
                .shadow(6.dp, CircleShape)
                .clip(CircleShape)
                .background(Color(0xFFF3F6FA)),
            contentAlignment = Alignment.Center
        ) {
            FilledIconButton(
                onClick = onAbrirConfiguracoes,
                modifier = Modifier.size(48.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFF004799),
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Configurações",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
