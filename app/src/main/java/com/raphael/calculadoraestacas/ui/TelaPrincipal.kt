package com.raphael.calculadoraestacas.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphael.calculadoraestacas.ui.components.PainelConfiguracoes
import com.raphael.calculadoraestacas.ui.components.SecaoEntradaEstaca
import com.raphael.calculadoraestacas.ui.components.SecaoResultado
import com.raphael.calculadoraestacas.ui.theme.CalculadoraEstacasTheme
import kotlinx.coroutines.launch

@Composable
fun TelaPrincipal(
    modifier: Modifier = Modifier,
    viewModel: CalculadoraViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val clipboardManager = LocalClipboardManager.current

    TelaPrincipalContent(
        state = state,
        snackbarHostState = snackbarHostState,
        onEstacaInicialChanged = viewModel::onEstacaInicialChanged,
        onMetrosEstacaInicialChanged = viewModel::onMetrosEstacaInicialChanged,
        onEstacaFinalChanged = viewModel::onEstacaFinalChanged,
        onMetrosEstacaFinalChanged = viewModel::onMetrosEstacaFinalChanged,
        onLimparCampos = viewModel::onLimparCampos,
        onAbrirConfiguracoes = viewModel::onAbrirConfiguracoes,
        onFecharConfiguracoes = viewModel::onFecharConfiguracoes,
        onConfiguracaoMetrosChanged = viewModel::onConfiguracaoMetrosChanged,
        onConfiguracaoPermitirNegativoChanged = viewModel::onConfiguracaoPermitirNegativoChanged,
        onRedefinirConfiguracoes = viewModel::onRedefinirConfiguracoes,
        onSalvarConfiguracoes = {
            viewModel.salvarConfiguracoes {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Configurações salvas")
                }
            }
        },
        onCopiarResultado = {
            clipboardManager.setText(AnnotatedString(state.textoResultado))
            coroutineScope.launch {
                snackbarHostState.showSnackbar("Resultado copiado")
            }
        },
        modifier = modifier
    )
}

@Composable
fun TelaPrincipalContent(
    state: CalculadoraUiState,
    snackbarHostState: SnackbarHostState,
    onEstacaInicialChanged: (String) -> Unit,
    onMetrosEstacaInicialChanged: (String) -> Unit,
    onEstacaFinalChanged: (String) -> Unit,
    onMetrosEstacaFinalChanged: (String) -> Unit,
    onLimparCampos: () -> Unit,
    onAbrirConfiguracoes: () -> Unit,
    onFecharConfiguracoes: () -> Unit,
    onConfiguracaoMetrosChanged: (String) -> Unit,
    onConfiguracaoPermitirNegativoChanged: (Boolean) -> Unit,
    onRedefinirConfiguracoes: () -> Unit,
    onSalvarConfiguracoes: () -> Unit,
    onCopiarResultado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

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
                        onClick = onAbrirConfiguracoes,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(text = "⚙")
                    }
                }
                Text(
                    text = "Calculadora de estacas",
                    style = MaterialTheme.typography.titleLarge
                )
            }

            SecaoEntradaEstaca(
                titulo = "Estaca Inicial",
                valorEstacas = state.estacaInicial,
                onEstacasChange = onEstacaInicialChanged,
                valorMetros = state.metrosEstacaInicial,
                onMetrosChange = onMetrosEstacaInicialChanged
            )

            SecaoEntradaEstaca(
                titulo = "Estaca final",
                valorEstacas = state.estacaFinal,
                onEstacasChange = onEstacaFinalChanged,
                valorMetros = state.metrosEstacaFinal,
                onMetrosChange = onMetrosEstacaFinalChanged
            )

            SecaoResultado(
                textoResultado = state.textoResultado,
                metrosPorEstaca = state.configuracoesSalvas.metrosPorEstaca,
                onLimpar = onLimparCampos,
                onCopiar = onCopiarResultado
            )
        }

        PainelConfiguracoes(
            visivel = state.configuracoesAbertas,
            metrosPorEstaca = state.configuracoesTempMetrosPorEstaca,
            onMetrosPorEstacaChange = onConfiguracaoMetrosChanged,
            permitirResultadoNegativo = state.configuracoesTempPermitirNegativo,
            onPermitirResultadoNegativoChange = onConfiguracaoPermitirNegativoChanged,
            podeSalvar = state.podeSalvarConfiguracoes,
            onFechar = onFecharConfiguracoes,
            onRedefinir = onRedefinirConfiguracoes,
            onSalvar = onSalvarConfiguracoes
        )

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
fun TelaPrincipalPreview() {
    CalculadoraEstacasTheme {
        TelaPrincipalContent(
            state = CalculadoraUiState(textoResultado = "40,00 m"),
            snackbarHostState = remember { SnackbarHostState() },
            onEstacaInicialChanged = {},
            onMetrosEstacaInicialChanged = {},
            onEstacaFinalChanged = {},
            onMetrosEstacaFinalChanged = {},
            onLimparCampos = {},
            onAbrirConfiguracoes = {},
            onFecharConfiguracoes = {},
            onConfiguracaoMetrosChanged = {},
            onConfiguracaoPermitirNegativoChanged = {},
            onRedefinirConfiguracoes = {},
            onSalvarConfiguracoes = {},
            onCopiarResultado = {}
        )
    }
}
