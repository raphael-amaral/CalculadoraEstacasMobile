package com.raphael.calculadoraestacas.ui

import com.raphael.calculadoraestacas.Configuracoes
import com.raphael.calculadoraestacas.domain.CalculadoraEstacas

data class CalculadoraUiState(
    val estacaInicial: String = "",
    val metrosEstacaInicial: String = "",
    val estacaFinal: String = "",
    val metrosEstacaFinal: String = "",
    val configuracoesAbertas: Boolean = false,
    val configuracoesTempMetrosPorEstaca: String = "20",
    val configuracoesTempPermitirNegativo: Boolean = false,
    val configuracoesSalvas: Configuracoes = Configuracoes(),
    val distanciaCalculada: Double? = null,
    val textoResultado: String = "0,00 m"
) {
    val podeSalvarConfiguracoes: Boolean
        get() {
            val metros = CalculadoraEstacas.parseDecimal(configuracoesTempMetrosPorEstaca)
            return metros != null && metros > 0.0 && metros.isFinite()
        }
}
