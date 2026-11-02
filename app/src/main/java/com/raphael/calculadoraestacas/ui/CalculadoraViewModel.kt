package com.raphael.calculadoraestacas.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.raphael.calculadoraestacas.Configuracoes
import com.raphael.calculadoraestacas.ConfiguracoesDataStore
import com.raphael.calculadoraestacas.domain.CalculadoraEstacas
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CalculadoraViewModel(
    application: Application,
    private val dataStore: ConfiguracoesDataStore
) : AndroidViewModel(application) {

    constructor(application: Application) : this(application, ConfiguracoesDataStore(application))

    private val _uiState = MutableStateFlow(CalculadoraUiState())
    val uiState: StateFlow<CalculadoraUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStore.dados.collect { configuracoes ->
                _uiState.update { estado ->
                    val estadoAtualizado = estado.copy(configuracoesSalvas = configuracoes)
                    recalcular(estadoAtualizado)
                }
            }
        }
    }

    fun onEstacaInicialChanged(valor: String) {
        if (CalculadoraEstacas.validarEntradaInteira(valor)) {
            _uiState.update { recalcular(it.copy(estacaInicial = valor)) }
        }
    }

    fun onMetrosEstacaInicialChanged(valor: String) {
        if (CalculadoraEstacas.validarEntradaDecimal(valor)) {
            _uiState.update { recalcular(it.copy(metrosEstacaInicial = valor)) }
        }
    }

    fun onEstacaFinalChanged(valor: String) {
        if (CalculadoraEstacas.validarEntradaInteira(valor)) {
            _uiState.update { recalcular(it.copy(estacaFinal = valor)) }
        }
    }

    fun onMetrosEstacaFinalChanged(valor: String) {
        if (CalculadoraEstacas.validarEntradaDecimal(valor)) {
            _uiState.update { recalcular(it.copy(metrosEstacaFinal = valor)) }
        }
    }

    fun onLimparCampos() {
        _uiState.update { estado ->
            val estadoLimpo = estado.copy(
                estacaInicial = "",
                metrosEstacaInicial = "",
                estacaFinal = "",
                metrosEstacaFinal = ""
            )
            recalcular(estadoLimpo)
        }
    }

    fun onAbrirConfiguracoes() {
        _uiState.update { estado ->
            estado.copy(
                configuracoesAbertas = true,
                configuracoesTempMetrosPorEstaca = estado.configuracoesSalvas.metrosPorEstaca
                    .toString()
                    .removeSuffix(".0"),
                configuracoesTempPermitirNegativo = estado.configuracoesSalvas.permitirResultadoNegativo
            )
        }
    }

    fun onFecharConfiguracoes() {
        _uiState.update { it.copy(configuracoesAbertas = false) }
    }

    fun onConfiguracaoMetrosChanged(valor: String) {
        if (CalculadoraEstacas.validarEntradaDecimal(valor)) {
            _uiState.update { it.copy(configuracoesTempMetrosPorEstaca = valor) }
        }
    }

    fun onConfiguracaoPermitirNegativoChanged(permitir: Boolean) {
        _uiState.update { it.copy(configuracoesTempPermitirNegativo = permitir) }
    }

    fun onRedefinirConfiguracoes() {
        _uiState.update {
            it.copy(
                configuracoesTempMetrosPorEstaca = "20",
                configuracoesTempPermitirNegativo = false
            )
        }
    }

    fun salvarConfiguracoes(onSucesso: () -> Unit) {
        val metros = CalculadoraEstacas.parseDecimal(_uiState.value.configuracoesTempMetrosPorEstaca)
        if (metros != null && metros > 0.0 && metros.isFinite()) {
            val novasConfig = Configuracoes(
                metrosPorEstaca = metros,
                permitirResultadoNegativo = _uiState.value.configuracoesTempPermitirNegativo
            )
            viewModelScope.launch {
                dataStore.salvar(novasConfig)
                _uiState.update { it.copy(configuracoesAbertas = false) }
                onSucesso()
            }
        }
    }

    private fun recalcular(estado: CalculadoraUiState): CalculadoraUiState {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = estado.estacaInicial,
            metrosInicial = estado.metrosEstacaInicial,
            estacaFinal = estado.estacaFinal,
            metrosFinal = estado.metrosEstacaFinal,
            configuracoes = estado.configuracoesSalvas
        )
        return estado.copy(
            distanciaCalculada = distancia,
            textoResultado = CalculadoraEstacas.formatarDistancia(distancia)
        )
    }
}
