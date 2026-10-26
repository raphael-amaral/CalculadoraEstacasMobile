package com.raphael.calculadoraestacas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raphael.calculadoraestacas.ui.theme.CalculadoraEstacasTheme
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraEstacasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaPrincipal(
                        title = "Calculadora de estacas",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TelaPrincipal(title: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val configuracoesDataStore = remember(context) { ConfiguracoesDataStore(context) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val clipboardManager = LocalClipboardManager.current
    val focusManager = LocalFocusManager.current

    var estacaInicial by rememberSaveable { mutableStateOf("") }
    var metrosEstacaInicial by rememberSaveable { mutableStateOf("") }
    var estacaFinal by rememberSaveable { mutableStateOf("") }
    var metrosEstacaFinal by rememberSaveable { mutableStateOf("") }
    var configuracoesAbertas by rememberSaveable { mutableStateOf(false) }
    var metrosPorEstaca by rememberSaveable { mutableStateOf("20") }
    var permitirResultadoNegativo by rememberSaveable { mutableStateOf(false) }
    var configuracoesAplicadas by remember { mutableStateOf(Configuracoes()) }

    LaunchedEffect(configuracoesDataStore) {
        val configuracoesSalvas = configuracoesDataStore.dados.first()
        configuracoesAplicadas = configuracoesSalvas
        metrosPorEstaca = configuracoesSalvas.metrosPorEstaca.toString().removeSuffix(".0")
        permitirResultadoNegativo = configuracoesSalvas.permitirResultadoNegativo
    }

    val valorEstacaInicial = estacaInicial.toIntOrNull()
    val valorMetrosInicial = if (metrosEstacaInicial.isBlank()) {
        0.0
    } else {
        metrosEstacaInicial.replace(',', '.').toDoubleOrNull()
    }
    val valorEstacaFinal = estacaFinal.toIntOrNull()
    val valorMetrosFinal = if (metrosEstacaFinal.isBlank()) {
        0.0
    } else {
        metrosEstacaFinal.replace(',', '.').toDoubleOrNull()
    }
    val valorMetrosPorEstaca = metrosPorEstaca.replace(',', '.').toDoubleOrNull()

    val distanciaCalculada = if (
        valorEstacaInicial != null &&
        valorMetrosInicial != null &&
        valorEstacaFinal != null &&
        valorMetrosFinal != null &&
        configuracoesAplicadas.metrosPorEstaca > 0.0 &&
        configuracoesAplicadas.metrosPorEstaca.isFinite()
    ) {
        val posicaoInicial = valorEstacaInicial * configuracoesAplicadas.metrosPorEstaca + valorMetrosInicial
        val posicaoFinal = valorEstacaFinal * configuracoesAplicadas.metrosPorEstaca + valorMetrosFinal
        val diferenca = posicaoFinal - posicaoInicial
        if (configuracoesAplicadas.permitirResultadoNegativo) {
            diferenca
        } else {
            abs(diferenca)
        }
    } else {
        null
    }

    val formatadorResultado = DecimalFormat(
        "#,##0.00##############",
        DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"))
    ).apply {
        isGroupingUsed = true
        groupingSize = 3
    }

    val resultadoFormatado = distanciaCalculada?.let { formatadorResultado.format(it) }
        ?: formatadorResultado.format(0)
    val textoResultado = "$resultadoFormatado m"

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(focusManager) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(48.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    FilledIconButton(
                        onClick = {
                            metrosPorEstaca = configuracoesAplicadas.metrosPorEstaca
                                .toString()
                                .removeSuffix(".0")
                            permitirResultadoNegativo = configuracoesAplicadas.permitirResultadoNegativo
                            configuracoesAbertas = true
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(text = "⚙")
                    }
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Estaca Inicial",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Estacas")
                        OutlinedTextField(
                            value = estacaInicial,
                            onValueChange = { novoValor ->
                                if (novoValor.all { it.isDigit() }) {
                                    estacaInicial = novoValor
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Metros")
                        OutlinedTextField(
                            value = metrosEstacaInicial,
                            onValueChange = { novoValor ->
                                if (
                                    novoValor.count { it == ',' || it == '.' } <= 1 &&
                                    novoValor.all { it.isDigit() || it == ',' || it == '.' }
                                ) {
                                    metrosEstacaInicial = novoValor
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Estaca final",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.titleMedium
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Estacas")
                        OutlinedTextField(
                            value = estacaFinal,
                            onValueChange = { novoValor ->
                                if (novoValor.all { it.isDigit() }) {
                                    estacaFinal = novoValor
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "Metros")
                        OutlinedTextField(
                            value = metrosEstacaFinal,
                            onValueChange = { novoValor ->
                                if (
                                    novoValor.count { it == ',' || it == '.' } <= 1 &&
                                    novoValor.all { it.isDigit() || it == ',' || it == '.' }
                                ) {
                                    metrosEstacaFinal = novoValor
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Resultado: ",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "(${
                            configuracoesAplicadas.metrosPorEstaca
                                .toString()
                                .removeSuffix(".0")
                        } m/estaca)",
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    IconButton(
                        onClick = {
                            estacaInicial = ""
                            metrosEstacaInicial = ""
                            estacaFinal = ""
                            metrosEstacaFinal = ""
                        },
                        modifier = Modifier.align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteSweep,
                            contentDescription = "Limpar campos"
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.65f)
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = textoResultado,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 2.dp
                        )
                    }
                    IconButton(
                        onClick = {
                            clipboardManager.setText(AnnotatedString(textoResultado))
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Resultado copiado")
                            }
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copiar resultado"
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = configuracoesAbertas,
            enter = fadeIn(animationSpec = tween(250)),
            exit = fadeOut(animationSpec = tween(200))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.35f))
                    .clickable { configuracoesAbertas = false }
            )
        }

        AnimatedVisibility(
            visible = configuracoesAbertas,
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
                            onValueChange = { novoValor ->
                                if (
                                    novoValor.count { it == ',' || it == '.' } <= 1 &&
                                    novoValor.all { it.isDigit() || it == ',' || it == '.' }
                                ) {
                                    metrosPorEstaca = novoValor
                                }
                            },
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
                                        permitirResultadoNegativo = !permitirResultadoNegativo
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = permitirResultadoNegativo,
                                    onCheckedChange = { marcado ->
                                        permitirResultadoNegativo = marcado
                                    }
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
                            onClick = {
                                metrosPorEstaca = "20"
                                permitirResultadoNegativo = false
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Redefinir")
                        }
                        Button(
                            onClick = {
                                val configuracoesAtuais = Configuracoes(
                                    metrosPorEstaca = valorMetrosPorEstaca ?: 20.0,
                                    permitirResultadoNegativo = permitirResultadoNegativo
                                )
                                coroutineScope.launch {
                                    configuracoesDataStore.salvar(configuracoesAtuais)
                                    configuracoesAplicadas = configuracoesAtuais
                                    configuracoesAbertas = false
                                    snackbarHostState.showSnackbar("Configurações salvas")
                                }
                            },
                            enabled = valorMetrosPorEstaca != null &&
                                valorMetrosPorEstaca > 0.0 &&
                                valorMetrosPorEstaca.isFinite(),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = "Salvar")
                        }
                    }
                }
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculadoraEstacasTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            TelaPrincipal(
                title = "Calculadora de estacas",
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPrincipalPreview() {
    CalculadoraEstacasTheme {
        TelaPrincipal(title = "Calculadora de estacas")
    }
}
