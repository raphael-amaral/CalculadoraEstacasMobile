package com.raphael.calculadoraestacas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.raphael.calculadoraestacas.ui.TelaPrincipal
import com.raphael.calculadoraestacas.ui.theme.CalculadoraEstacasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
        )
        setContent {
            CalculadoraEstacasTheme {
                TelaPrincipal(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
