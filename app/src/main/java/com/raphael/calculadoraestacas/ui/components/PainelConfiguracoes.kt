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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
            .fillMaxWidth(0.8f)
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
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                    Text(
                        text = "Configurações",
                        style = MaterialTheme.typography.titleLarge
                    )
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
                            style = MaterialTheme.typography.titleMedium
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
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
    }
}
