package com.raphael.calculadoraestacas.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.IconButton
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BoxScope.PainelConfiguracoes(
    visivel: Boolean,
    metrosPorEstaca: String,
    onMetrosPorEstacaChange: (String) -> Unit,
    permitirResultadoNegativo: Boolean,
    onPermitirResultadoNegativoChange: (Boolean) -> Unit,
    podeSalvar: Boolean,
    onFechar: () -> Unit,
    onRedefinir: () -> Unit,
    onSalvar: () -> Unit
) {
    val uriHandler = LocalUriHandler.current

    AnimatedVisibility(
        visible = visivel,
        enter = fadeIn(animationSpec = tween(250)),
        exit = fadeOut(animationSpec = tween(200))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
                .clickable { onFechar() }
        )
    }

    AnimatedVisibility(
        visible = visivel,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.82f)
            .align(Alignment.CenterEnd),
        enter = slideInHorizontally(
            animationSpec = tween(300),
            initialOffsetX = { largura -> largura }
        ) + fadeIn(animationSpec = tween(250)),
        exit = slideOutHorizontally(
            animationSpec = tween(250),
            targetOffsetX = { largura -> largura }
        ) + fadeOut(animationSpec = tween(200))
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shadowElevation = 8.dp,
            color = Color.White
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                val topSafeInset = WindowInsets.safeDrawing.asPaddingValues().calculateTopPadding()
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(
                            start = 20.dp,
                            top = topSafeInset + 16.dp,
                            end = 20.dp,
                            bottom = 20.dp
                        ),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Configurações",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F2B48)
                            )
                            IconButton(
                                onClick = onFechar,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFFF1F5F9))
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Voltar / Fechar",
                                    tint = Color(0xFF0F2B48),
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        OutlinedTextField(
                            value = metrosPorEstaca,
                            onValueChange = onMetrosPorEstacaChange,
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text(text = "Metros por estaca") },
                            suffix = { Text(text = "m") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Resultado",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0F2B48)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        onPermitirResultadoNegativoChange(!permitirResultadoNegativo)
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = permitirResultadoNegativo,
                                    onCheckedChange = onPermitirResultadoNegativoChange
                                )
                                Text(text = "Permitir resultado negativo")
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        HorizontalDivider(color = Color(0xFFE2E8F0))

                        // Informações da versão e link para o repositório no GitHub
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Calculadora de Estacas • ",
                                fontSize = 13.sp,
                                color = Color(0xFF64748B)
                            )
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .clickable {
                                        uriHandler.openUri("https://github.com/raphael-amaral/CalculadoraEstacasMobile")
                                    }
                                    .padding(horizontal = 4.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "v1.1.0",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF0055AC)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                                    contentDescription = "Abrir repositório no GitHub",
                                    tint = Color(0xFF0055AC),
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = onRedefinir,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Redefinir")
                            }
                            Button(
                                onClick = onSalvar,
                                enabled = podeSalvar,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Salvar")
                            }
                        }
                    }
                }

                // Faixa escura na barra de navegação inferior para manter o contraste dos botões brancos do sistema
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .windowInsetsBottomHeight(WindowInsets.navigationBars)
                        .background(Color(0xFF063970))
                )
            }
        }
    }
}
