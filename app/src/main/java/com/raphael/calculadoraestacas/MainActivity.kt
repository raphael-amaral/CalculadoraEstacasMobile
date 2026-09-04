package com.raphael.calculadoraestacas

import androidx.compose.ui.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raphael.calculadoraestacas.ui.theme.CalculadoraEstacasTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraEstacasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaTeste("Olá", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TelaTeste(title: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.background(Color.Green).fillMaxSize()) {
        Text(text = title, modifier = Modifier.background(Color.Red).align(Alignment.CenterHorizontally))
        Text(text = "Estaca Inicial", modifier = Modifier.background(Color.Cyan).align(Alignment.CenterHorizontally))
        Row(modifier = Modifier.background(Color.Yellow).fillMaxWidth()){
            Button(onClick = {}, modifier = Modifier.background(Color.Blue)
                .weight(1f)
                .padding(5.dp))
            { Text("Botao1")}
            Button(onClick = {}, modifier = Modifier.background(Color.Red)
                .weight(1f)
                .padding(5.dp))
            { Text("Botao2")}
        }
        Text(text = "Estaca Final", modifier = Modifier.background(Color.Cyan).align(Alignment.CenterHorizontally))
        Row(modifier = Modifier.background(Color.Yellow)
            .fillMaxWidth()){
            Button(onClick = {}, modifier = Modifier.background(Color.Blue)
                .weight(1f)
                .padding(5.dp))
            { Text("Botao3")}
            Button(onClick = {}, modifier = Modifier.background(Color.Red)
                .weight(1f)
                .padding(5.dp))
            { Text("Botao4")}
        }
        Row(modifier = Modifier.background(Color.Yellow)
            .fillMaxWidth()){
        }
        Text(text = "Resultado: " + "(20 m/estaca)", modifier = Modifier.background(Color.Cyan)
            .align(Alignment.CenterHorizontally))
        Text(text = "___________________________________", modifier = Modifier.background(Color.Blue)
            .align(Alignment.CenterHorizontally))
    }
}

@Preview
@Composable
fun TelaTestePreview() {
    CalculadoraEstacasTheme {
        TelaTeste("Olá")
    }
}