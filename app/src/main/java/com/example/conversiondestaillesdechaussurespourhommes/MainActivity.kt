package com.example.conversiondestaillesdechaussurespourhommes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.conversiondestaillesdechaussurespourhommes.ui.theme.ConversionDesTaillesDeChaussuresPourHommesTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConversionDesTaillesDeChaussuresPourHommesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoeSizeConverter()

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConversionDesTaillesDeChaussuresPourHommesTheme {
        ShoeSizeConverter()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoeSizeConverter() {
    var inputSize by remember { mutableStateOf("") }
    var selectedUnit by remember { mutableStateOf(ShoeSizeUnit.UK) }
    var outputSize by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = inputSize,
            onValueChange = { inputSize = it },
            label = { Text("Taille de la chaussure") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (unit in ShoeSizeUnit.values()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    RadioButton(
                        selected = selectedUnit == unit,
                        onClick = { selectedUnit = unit },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
                    )

                    Text(unit.name)
                }
            }
        }

        Button(
            onClick = {
                val inputValue = inputSize.toDoubleOrNull() ?: return@Button
                outputSize = convertShoeSize(inputValue, selectedUnit)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Convertir")
        }

        if (outputSize.isNotEmpty()) {
            Text(
                text = "Taille convertie: $outputSize",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

enum class ShoeSizeUnit {
    UK,
    EU,
    CM,
    Chine,
    US,
    Canada
}

fun convertShoeSize(inputValue: Double, inputUnit: ShoeSizeUnit): String {
    val sizeInCM = when (inputUnit) {
        ShoeSizeUnit.UK -> ukToCm(inputValue)
        ShoeSizeUnit.EU -> euroToCm(inputValue)
        ShoeSizeUnit.CM -> inputValue
        ShoeSizeUnit.Chine -> inchesToCm(inputValue)
        ShoeSizeUnit.US -> usToCm(inputValue)
        ShoeSizeUnit.Canada -> canadaToCm(inputValue)
    }

    return String.format("%.2f", sizeInCM)
}

fun ukToCm(size: Double): Double = size * 2.54
fun euroToCm(size: Double): Double = size * 0.65
fun inchesToCm(size: Double): Double = size * 2.54
fun usToCm(size: Double): Double = size * 0.65
fun canadaToCm(size: Double): Double = size * 0.65