package com.raphael.calculadoraestacas.domain

import com.raphael.calculadoraestacas.Configuracoes
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.abs

object CalculadoraEstacas {

    private val formatadorResultado = DecimalFormat(
        "#,##0.00##############",
        DecimalFormatSymbols.getInstance(Locale.forLanguageTag("pt-BR"))
    ).apply {
        isGroupingUsed = true
        groupingSize = 3
    }

    fun validarEntradaInteira(texto: String): Boolean {
        return texto.all { it.isDigit() }
    }

    fun validarEntradaDecimal(texto: String): Boolean {
        val pontuacoes = texto.count { it == ',' || it == '.' }
        return pontuacoes <= 1 && texto.all { it.isDigit() || it == ',' || it == '.' }
    }

    fun parseDecimal(texto: String): Double? {
        if (texto.isBlank()) return 0.0
        return texto.replace(',', '.').toDoubleOrNull()
    }

    fun calcularDistancia(
        estacaInicial: String,
        metrosInicial: String,
        estacaFinal: String,
        metrosFinal: String,
        configuracoes: Configuracoes
    ): Double? {
        val numEstacaInicial = estacaInicial.toIntOrNull() ?: return null
        val numMetrosInicial = parseDecimal(metrosInicial) ?: return null

        val numEstacaFinal = estacaFinal.toIntOrNull() ?: return null
        val numMetrosFinal = parseDecimal(metrosFinal) ?: return null

        val metrosPorEstaca = configuracoes.metrosPorEstaca
        if (metrosPorEstaca <= 0.0 || !metrosPorEstaca.isFinite()) return null

        val posicaoInicial = numEstacaInicial * metrosPorEstaca + numMetrosInicial
        val posicaoFinal = numEstacaFinal * metrosPorEstaca + numMetrosFinal
        val diferenca = posicaoFinal - posicaoInicial

        return if (configuracoes.permitirResultadoNegativo) {
            diferenca
        } else {
            abs(diferenca)
        }
    }

    fun formatarDistancia(distancia: Double?): String {
        val valorFormatado = distancia?.let { formatadorResultado.format(it) }
            ?: formatadorResultado.format(0)
        return "$valorFormatado m"
    }
}
