@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.resistencias

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.resistencias.ui.theme.ResistenciasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ResistenciasTheme {
                DropDown()
            }
        }
    }
}

@Composable
fun DropDown() {
    val Orange = Color(0xFFF57F00)   // Naranja más suave
    val Purple = Color(0xFF9C27B0)   // Púrpura más suave
    val Brown = Color(0xFF8D6E63)    // Marrón más suave

    val listBanda1 = listOf(
        "Negro 0", "Marrón 1", "Rojo 2", "Naranja 3", "Amarillo 4",
        "Verde 5", "Azul 6", "Violeta 7", "Gris 8", "Blanco 9"
    )

    val bandaColores = listBanda1.associate {
        val (color, valor) = it.split(" ")
        color to valor.toInt()
    }
    val listMultiplicador = listOf("x1", "x10", "x100", "x1000", "x10,000", "x100,000", "x1,000,000", "x10,000,000", "x100,000,000", "x1000,000,000", "x0.1", "0.01")

    val multiplicadorValores = listMultiplicador.associate { multiplicador ->
        val valor = multiplicador.replace(",", "").replace("x", "") // Eliminamos las comas y la "x"
        multiplicador to valor.toDouble() // Convertimos a Double
    }

    val listBandaTolerancia = listOf("± 1%","± 2%","± 0.5%","± 0.25%","± 0.10%","± 0.05%","± 5%","± 10%","± 20%")

    val listBanda1Colors = listOf(
        Color.Black, Brown, Color.Red, Orange, Color.Yellow,
        Color.Green, Color.Blue, Purple, Color.Gray, Color.White
    )

    val additionalColors = listOf(Color(0xFFFFD700), Color(0xFFC0C0C0))
    val listMultiplicadorColor = listBanda1Colors.toList() + additionalColors
    val listBandaToleranciaColor = listOf(
        Color.Black, Brown, Color.Green, Color.Blue, Color.Magenta, Color.Gray, Color(0xFFFFD700), Color(0xFFC0C0C0), Color.Transparent
    )

    var selectedBanda1 by remember { mutableStateOf(listBanda1[0]) }
    var selectedBanda2 by remember { mutableStateOf(listBanda1[0]) }
    var selectedText by remember { mutableStateOf(listMultiplicador[0]) }
    var selectedText2 by remember { mutableStateOf(listBandaTolerancia[0]) }

    var selectedColor by remember { mutableStateOf(listBanda1Colors[0]) }
    var selectedColor2 by remember { mutableStateOf(listBanda1Colors[0]) }
    var selectedColor3 by remember { mutableStateOf(listMultiplicadorColor[0]) }
    var selectedColor4 by remember { mutableStateOf(listBandaToleranciaColor[0]) }

    var isExpanded1 by remember { mutableStateOf(false) }
    var isExpanded2 by remember { mutableStateOf(false) }
    var isExpanded3 by remember { mutableStateOf(false) }
    var isExpanded4 by remember { mutableStateOf(false) }

    // Lógica de cálculo de la resistencia
    val valorBanda1 = bandaColores[selectedBanda1.split(" ")[0]] ?: 0
    val valorBanda2 = bandaColores[selectedBanda2.split(" ")[0]] ?: 0
    val valorMultiplicador = multiplicadorValores[selectedText] ?: 1.0

    val valorResistencia = (valorBanda1 * 10 + valorBanda2) * valorMultiplicador
    val valorTolerancia = selectedText2

    // Función para reiniciar los valores
    fun resetValues() {
        selectedBanda1 = listBanda1[0]
        selectedBanda2 = listBanda1[0]
        selectedText = listMultiplicador[0]
        selectedText2 = listBandaTolerancia[0]
        selectedColor = listBanda1Colors[0]
        selectedColor2 = listBanda1Colors[0]
        selectedColor3 = listMultiplicadorColor[0]
        selectedColor4 = listBandaToleranciaColor[0]
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFE0E0E0)) // Fondo gris claro
            .border(2.dp, Color.Black, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp)) // Espacio en la parte superior

        Text(
            text = "Calculadora de Código de Colores de Resistencias",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Primer lista desplegable centrada con marco ajustado
        ExposedDropdownMenuBox(
            expanded = isExpanded1,
            onExpandedChange = { isExpanded1 = !isExpanded1 },
            modifier = Modifier
                .widthIn(min = 200.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor, shape = RoundedCornerShape(16.dp)),
                value = selectedBanda1,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Banda 1") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded1) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded1,
                onDismissRequest = { isExpanded1 = false }
            ) {
                listBanda1.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBanda1Colors[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                color = if (listBanda1Colors[index] == Color.Black) Color.White else Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedBanda1 = listBanda1[index]
                            selectedColor = listBanda1Colors[index]
                            isExpanded1 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Segunda lista centrada
        ExposedDropdownMenuBox(
            expanded = isExpanded2,
            onExpandedChange = { isExpanded2 = !isExpanded2 },
            modifier = Modifier
                .widthIn(min = 200.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor2, shape = RoundedCornerShape(16.dp)),
                value = selectedBanda2,
                onValueChange = {},
                readOnly = true,
                label = { Text("Selecciona Banda 2") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded2) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded2,
                onDismissRequest = { isExpanded2 = false }
            ) {
                listBanda1.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBanda1Colors[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                color = if (listBanda1Colors[index] == Color.Black) Color.White else Color.Black,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedBanda2 = listBanda1[index]
                            selectedColor2 = listBanda1Colors[index]
                            isExpanded2 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Multiplicador
        ExposedDropdownMenuBox(
            expanded = isExpanded3,
            onExpandedChange = { isExpanded3 = !isExpanded3 },
            modifier = Modifier
                .widthIn(min = 200.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor3, shape = RoundedCornerShape(16.dp)),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                label = { Text("Multiplicador") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded3) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor3,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded3,
                onDismissRequest = { isExpanded3 = false }
            ) {
                listMultiplicador.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listMultiplicadorColor[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedText = listMultiplicador[index]
                            selectedColor3 = listMultiplicadorColor[index]
                            isExpanded3 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tolerancia
        ExposedDropdownMenuBox(
            expanded = isExpanded4,
            onExpandedChange = { isExpanded4 = !isExpanded4 },
            modifier = Modifier
                .widthIn(min = 200.dp)
                .border(2.dp, Color.Black, RoundedCornerShape(16.dp))
                .padding(8.dp)
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .background(selectedColor4, shape = RoundedCornerShape(16.dp)),
                value = selectedText2,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tolerancia") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded4) },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = selectedColor4,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded4,
                onDismissRequest = { isExpanded4 = false }
            ) {
                listBandaTolerancia.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(listBandaToleranciaColor[index])
                            .padding(vertical = 8.dp),
                        text = {
                            Text(
                                text = text,
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
                        onClick = {
                            selectedText2 = listBandaTolerancia[index]
                            selectedColor4 = listBandaToleranciaColor[index]
                            isExpanded4 = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Resultado
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Resistencia: ${"%.2f".format(valorResistencia)} Ω",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tolerancia: $valorTolerancia",
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para reiniciar los valores
        Button(
            onClick = { resetValues() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF009688)), // Color verde aqua
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(50.dp)
        ) {
            Icon(Icons.Filled.Refresh, contentDescription = "Reiniciar Valores")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Reiniciar Valores")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Información adicional
        Text(
            text = "Para calcular la resistencia, selecciona los valores de las bandas y el multiplicador. La tolerancia indica el rango en el que la resistencia puede variar.",
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
    }
}
