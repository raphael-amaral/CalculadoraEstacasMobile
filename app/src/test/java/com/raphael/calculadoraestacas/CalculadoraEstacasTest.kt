package com.raphael.calculadoraestacas

import com.raphael.calculadoraestacas.domain.CalculadoraEstacas
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CalculadoraEstacasTest {

    private val configPadrao = Configuracoes(metrosPorEstaca = 20.0, permitirResultadoNegativo = false)

    @Test
    fun calcularDistancia_estacasExatas_retornaDistanciaCorreta() {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = "0",
            metrosInicial = "",
            estacaFinal = "5",
            metrosFinal = "",
            configuracoes = configPadrao
        )

        // 5 * 20m = 100m
        assertEquals(100.0, distancia ?: 0.0, 0.0001)
    }

    @Test
    fun calcularDistancia_comFracoesEmMetros_calculaPrecisamente() {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = "10",
            metrosInicial = "5,5",
            estacaFinal = "12",
            metrosFinal = "10,2",
            configuracoes = configPadrao
        )

        // Posicao inicial: 10 * 20 + 5.5 = 205.5
        // Posicao final: 12 * 20 + 10.2 = 250.2
        // Distancia = 44.7
        assertEquals(44.7, distancia ?: 0.0, 0.0001)
    }

    @Test
    fun calcularDistancia_finalMenorQueInicial_semPermitirNegativo_retornaValorAbsoluto() {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = "10",
            metrosInicial = "0",
            estacaFinal = "5",
            metrosFinal = "0",
            configuracoes = configPadrao.copy(permitirResultadoNegativo = false)
        )

        assertEquals(100.0, distancia ?: 0.0, 0.0001)
    }

    @Test
    fun calcularDistancia_finalMenorQueInicial_comPermitirNegativo_retornaValorNegativo() {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = "10",
            metrosInicial = "0",
            estacaFinal = "5",
            metrosFinal = "0",
            configuracoes = configPadrao.copy(permitirResultadoNegativo = true)
        )

        assertEquals(-100.0, distancia ?: 0.0, 0.0001)
    }

    @Test
    fun calcularDistancia_campoEstacaIncompleto_retornaNull() {
        val distancia = CalculadoraEstacas.calcularDistancia(
            estacaInicial = "",
            metrosInicial = "10",
            estacaFinal = "5",
            metrosFinal = "0",
            configuracoes = configPadrao
        )

        assertNull(distancia)
    }

    @Test
    fun formatarDistancia_formataCorretamenteComUnidade() {
        val textoFormatado = CalculadoraEstacas.formatarDistancia(1250.5)
        assertEquals("1.250,50 m", textoFormatado)

        val textoPadraoZero = CalculadoraEstacas.formatarDistancia(null)
        assertEquals("0,00 m", textoPadraoZero)
    }

    @Test
    fun validacoesDeEntrada_validamCorretamenteInteirosEDecimais() {
        assertTrue(CalculadoraEstacas.validarEntradaInteira("12345"))
        assertFalse(CalculadoraEstacas.validarEntradaInteira("12a3"))
        assertFalse(CalculadoraEstacas.validarEntradaInteira("12.3"))

        assertTrue(CalculadoraEstacas.validarEntradaDecimal("12,5"))
        assertTrue(CalculadoraEstacas.validarEntradaDecimal("12.5"))
        assertFalse(CalculadoraEstacas.validarEntradaDecimal("12,5,3"))
        assertFalse(CalculadoraEstacas.validarEntradaDecimal("abc"))
    }
}
