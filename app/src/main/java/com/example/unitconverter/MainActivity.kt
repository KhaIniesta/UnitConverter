package com.example.unitconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                UnitConverter()
            }
        }
    }
}

@Composable
fun UnitConverter() {
    val unitFactors = listOf(
        "Centimeter" to 1.0,
        "Feet" to 30.48,
        "Meter" to 100.0
    )
    val inputContent = remember { mutableStateOf("") }
    val inputValue = remember { mutableDoubleStateOf(0.0) }
    val result = remember { mutableDoubleStateOf(0.0) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Unit Converter",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputContent.value,
            onValueChange = {
                inputContent.value = it
            },
            label = {
                Text(text = "Enter value")
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            UnitDropDown(unitFactors = unitFactors, inputContent = inputContent, inputValue = inputValue, result = result, isInputSelect = true)
            Spacer(modifier = Modifier.width(16.dp))
            UnitDropDown(unitFactors = unitFactors, inputContent = inputContent, inputValue = inputValue, result = result, isInputSelect = false)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Result: ", fontSize = 20.sp)
            Text(
                text = "${result.doubleValue}",
                color = Color.Green,
                fontSize = 25.sp
            )
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Composable
fun UnitConverterPreview() {
    UnitConverterTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            UnitConverter()
        }
    }
}

@Composable
fun UnitDropDown(
    unitFactors: List<Pair<String, Double>>,
    inputContent: MutableState<String>,
    inputValue: MutableDoubleState,
    result: MutableDoubleState,
    isInputSelect: Boolean
) {
    Box {
        var isDropdownMenuExpanded by remember { mutableStateOf(false) }
        var buttonContent by remember {
            mutableStateOf(if (isInputSelect) "In Unit" else "Out Unit")
        }
        Button(modifier = Modifier.width(135.dp), onClick = {
            isDropdownMenuExpanded = true
        }) {
            Text(buttonContent)
            Icon(Icons.Default.ArrowDropDown, "Arrow Down")
        }

        DropdownMenu(expanded = isDropdownMenuExpanded, onDismissRequest = {
            isDropdownMenuExpanded = false
            buttonContent = "Input Unit"
        }) {
            for ((unit, factor) in unitFactors) {
                DropdownMenuItem(
                    text = {
                        Text(text = unit)
                    },
                    onClick = {
                        buttonContent = unit
                        isDropdownMenuExpanded = false

                        if (isInputSelect) {
                            if (inputContent.value.toDoubleOrNull() != null) {
                                inputValue.doubleValue = inputContent.value.toDouble() * factor
                            }
                        }
                        else {
                            result.doubleValue = inputValue.doubleValue / factor
                        }
                    }
                )
            }
        }
    }
}
