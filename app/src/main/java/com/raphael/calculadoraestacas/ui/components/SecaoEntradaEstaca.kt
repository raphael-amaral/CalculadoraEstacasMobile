package com.raphael.calculadoraestacas.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecaoEntradaEstaca(
    numeroEtapa: String,
    corBadge: Color,
    corFundoCabecalho: Color,
    titulo: String,
    valorEstacas: String,
    onEstacasChange: (String) -> Unit,
    valorMetros: String,
    onMetrosChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    paddingVerticalCorpo: androidx.compose.ui.unit.Dp = 12.dp,
    focusRequesterEstacas: FocusRequester = remember { FocusRequester() },
    focusRequesterMetros: FocusRequester = remember { FocusRequester() },
    imeActionMetros: ImeAction = ImeAction.Next,
    onAvancarEstacas: () -> Unit = {},
    onAvancarMetros: () -> Unit = {}
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Faixa superior colorida do Card: Badge com número + Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(corFundoCabecalho)
                    .padding(horizontal = 16.dp, vertical = 9.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(corBadge),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = numeroEtapa,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF0F2B48)
                )
            }

            // Corpo branco do Card com os dois campos (Estacas e Metros)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = paddingVerticalCorpo),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Campo Estacas
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Layers,
                            contentDescription = null,
                            tint = Color(0xFF004F9E),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Estacas",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color(0xFF334155)
                        )
                    }

                    OutlinedTextField(
                        value = valorEstacas,
                        onValueChange = onEstacasChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterEstacas),
                        placeholder = { Text(text = "0", color = Color(0xFF94A3B8)) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { onAvancarEstacas() },
                            onDone = { onAvancarEstacas() }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFCFF),
                            unfocusedContainerColor = Color(0xFFFAFCFF),
                            focusedBorderColor = corBadge,
                            unfocusedBorderColor = Color(0xFFD5E0EB)
                        )
                    )
                }

                // Campo Metros
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Straighten,
                            contentDescription = null,
                            tint = Color(0xFF004F9E),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Metros",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = Color(0xFF334155)
                        )
                    }

                    OutlinedTextField(
                        value = valorMetros,
                        onValueChange = onMetrosChange,
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequesterMetros),
                        placeholder = { Text(text = "0,00", color = Color(0xFF94A3B8)) },
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal,
                            imeAction = imeActionMetros
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { onAvancarMetros() },
                            onDone = { onAvancarMetros() }
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color(0xFFFAFCFF),
                            unfocusedContainerColor = Color(0xFFFAFCFF),
                            focusedBorderColor = corBadge,
                            unfocusedBorderColor = Color(0xFFD5E0EB)
                        )
                    )
                }
            }
        }
    }
}
