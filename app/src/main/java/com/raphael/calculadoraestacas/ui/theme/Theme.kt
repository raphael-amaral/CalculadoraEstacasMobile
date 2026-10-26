package com.raphael.calculadoraestacas.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = AzulPrincipal,
    onPrimary = SobreAzulPrincipal,
    primaryContainer = AzulClaro,
    onPrimaryContainer = SobreAzulClaro,
    secondary = AzulSecundario,
    onSecondary = SobreAzulSecundario,
    secondaryContainer = AzulSecundarioClaro,
    onSecondaryContainer = SobreAzulSecundarioClaro,
    tertiary = VerdeDestaque,
    onTertiary = SobreVerdeDestaque,
    background = FundoClaro,
    onBackground = TextoPrincipal,
    surface = SuperficieClara,
    onSurface = TextoPrincipal,
    surfaceVariant = SuperficieAlternativa,
    onSurfaceVariant = TextoSecundario,
    outline = Contorno
)

@Composable
fun CalculadoraEstacasTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
