package com.raphael.calculadoraestacas.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.raphael.calculadoraestacas.ui.components.HeaderApp
import com.raphael.calculadoraestacas.ui.components.PainelConfiguracoes
import com.raphael.calculadoraestacas.ui.components.RodapeOnda
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
    val uriHandler = LocalUriHandler.current
    val scrollState = rememberScrollState()

    val focusRequesterEstacaInicial = remember { FocusRequester() }
    val focusRequesterMetrosInicial = remember { FocusRequester() }
    val focusRequesterEstacaFinal = remember { FocusRequester() }
    val focusRequesterMetrosFinal = remember { FocusRequester() }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF3F6FA))
            .pointerInput(focusManager) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            }
    ) {
        val isTallScreen = maxHeight >= 780.dp
        val alturaBaseHeader = if (isTallScreen) 130.dp else 114.dp
        val espacamentoCards = if (isTallScreen) 14.dp else 10.dp
        val paddingVerticalCorpo = if (isTallScreen) 13.dp else 10.dp
        val tamanhoFonteResultado = if (isTallScreen) 30.sp else 26.sp
        val paddingVerticalResultado = if (isTallScreen) 9.dp else 6.dp

        // Onda decorativa fixa na base da tela (preenchendo até a borda inferior)
        RodapeOnda(
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .navigationBarsPadding()
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cabeçalho curvo com degradê azul
            HeaderApp(
                onAbrirConfiguracoes = onAbrirConfiguracoes,
                alturaBase = alturaBaseHeader
            )

            // Conteúdo principal com os cards estilizados e a assinatura integrada
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = if (isTallScreen) 8.dp else 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(espacamentoCards)
            ) {
                // Card 1: Estaca Inicial (Destaque Azul)
                SecaoEntradaEstaca(
                    numeroEtapa = "1",
                    corBadge = Color(0xFF004799),
                    corFundoCabecalho = Color(0xFFE9F2FB),
                    titulo = "Estaca Inicial",
                    valorEstacas = state.estacaInicial,
                    onEstacasChange = onEstacaInicialChanged,
                    valorMetros = state.metrosEstacaInicial,
                    onMetrosChange = onMetrosEstacaInicialChanged,
                    paddingVerticalCorpo = paddingVerticalCorpo,
                    focusRequesterEstacas = focusRequesterEstacaInicial,
                    focusRequesterMetros = focusRequesterMetrosInicial,
                    imeActionMetros = ImeAction.Next,
                    onAvancarEstacas = { focusRequesterMetrosInicial.requestFocus() },
                    onAvancarMetros = { focusRequesterEstacaFinal.requestFocus() }
                )

                // Card 2: Estaca Final (Destaque Azul harmônico com a Estaca Inicial)
                SecaoEntradaEstaca(
                    numeroEtapa = "2",
                    corBadge = Color(0xFF004799),
                    corFundoCabecalho = Color(0xFFE9F2FB),
                    titulo = "Estaca Final",
                    valorEstacas = state.estacaFinal,
                    onEstacasChange = onEstacaFinalChanged,
                    valorMetros = state.metrosEstacaFinal,
                    onMetrosChange = onMetrosEstacaFinalChanged,
                    paddingVerticalCorpo = paddingVerticalCorpo,
                    focusRequesterEstacas = focusRequesterEstacaFinal,
                    focusRequesterMetros = focusRequesterMetrosFinal,
                    imeActionMetros = ImeAction.Done,
                    onAvancarEstacas = { focusRequesterMetrosFinal.requestFocus() },
                    onAvancarMetros = { focusManager.clearFocus() }
                )

                // Card 3: Resultado destacado com fundo verde menta
                SecaoResultado(
                    textoResultado = state.textoResultado,
                    metrosPorEstaca = state.configuracoesSalvas.metrosPorEstaca,
                    tamanhoFonte = tamanhoFonteResultado,
                    paddingVerticalCorpo = paddingVerticalResultado,
                    onLimpar = onLimparCampos,
                    onCopiar = onCopiarResultado
                )

                Spacer(modifier = Modifier.height(if (isTallScreen) 6.dp else 2.dp))

                // Assinatura clicável direcionando para o perfil do GitHub
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            uriHandler.openUri("https://github.com/raphael-amaral")
                        }
                        .padding(horizontal = 12.dp, vertical = 2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Desenvolvido por",
                        fontSize = 11.sp,
                        color = Color(0xFF64748B)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Raphael do Amaral Nunes",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0055AC)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.OpenInNew,
                            contentDescription = "Abrir perfil de Raphael no GitHub",
                            tint = Color(0xFF0055AC),
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }

        // Painel deslizante de Configurações
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
                .navigationBarsPadding()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TelaPrincipalPreview() {
    CalculadoraEstacasTheme {
        TelaPrincipalContent(
            state = CalculadoraUiState(
                estacaInicial = "3",
                metrosEstacaInicial = "2.55",
                estacaFinal = "6",
                metrosEstacaFinal = "3.15",
                textoResultado = "60,60 m"
            ),
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
