package com.calculadora

import android.os.Bundle
import java.util.Locale
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

//Fuentes
val letracustom = FontFamily(
    Font(R.font.sansbold, FontWeight.Normal),
    )

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {

    //Codigo logico
    var displayValue by remember { mutableStateOf("0") }
    var currentNumber: Double? by remember { mutableStateOf(null) }
    var previousNumber: Double? by remember { mutableStateOf(null) }
    var currentOperation: String? by remember { mutableStateOf(null) }
    var newCalculationStarted by remember { mutableStateOf(false) }

    fun onNumberClick(number: Int) {
        if (newCalculationStarted) {
            displayValue = number.toString()
            newCalculationStarted = false
        } else if (displayValue == "0" && number == 0) {
            displayValue = "0"
        } else if (displayValue == "0") {
            displayValue = number.toString()
        } else {
            displayValue += number.toString()
        }
    }

    fun onDecimalClick() {
        if (newCalculationStarted) {
            displayValue = "0."
            newCalculationStarted = false
        } else if (!displayValue.contains(".")) {
            displayValue += "."
        }
    }

    fun onOperatorClick(operation: String) {
        currentNumber = displayValue.toDoubleOrNull()
        if (currentNumber != null) {
            previousNumber = currentNumber
            currentOperation = operation
            newCalculationStarted = true
        }
    }

    fun onEqualsClick() {
        val num1 = previousNumber ?: return
        val num2 = displayValue.toDoubleOrNull() ?: return
        var result: Double? = null

        when (currentOperation) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "*" -> result = num1 * num2
            "/" -> {
                if (num2 != 0.0) {
                    result = num1 / num2
                } else {
                    displayValue = "Error"
                    currentNumber = null
                    previousNumber = null
                    currentOperation = null
                    newCalculationStarted = true
                    return
                }
            }
            "%" -> result = num1 * (num2 / 100.0)
        }

        if (result != null) {
            displayValue = if (result == result.toLong().toDouble()) {
                result.toLong().toString()
            } else {
                String.format(Locale.US, "%.6f", result).toDouble().toString()
            }
            currentNumber = result
            previousNumber = null
            currentOperation = null
            newCalculationStarted = true
        }
    }

    fun onClearClick() {
        displayValue = "0"
        currentNumber = null
        previousNumber = null
        currentOperation = null
        newCalculationStarted = false
    }

    //Interfaz
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0F172A)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Pantalla de resultados
            Text(
                text = displayValue,
                fontSize = 80.sp,
                color = Color.White,
                fontFamily = letracustom,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 24.dp)
                    .wrapContentHeight(Alignment.Bottom)
                    .wrapContentWidth(Alignment.End),
                textAlign = TextAlign.End
            )

            // Fila de simbolos
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalculatorButton(text = "C", onClick = { onClearClick() }, isOperation = true, fontFamily = letracustom)
                CalculatorButton(text = "±", onClick = {}, isOperation = true, fontFamily = letracustom)
                CalculatorButton(text = "%", onClick = { onOperatorClick("%") }, isOperation = true, fontFamily = letracustom)
                CalculatorButton(text = "÷", onClick = { onOperatorClick("/") }, isOperation = true, fontFamily = letracustom)
            }

            // Fila de numeros del 7 al 9
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalculatorButton(text = "7", onClick = { onNumberClick(7) }, fontFamily = letracustom)
                CalculatorButton(text = "8", onClick = { onNumberClick(8) }, fontFamily = letracustom)
                CalculatorButton(text = "9", onClick = { onNumberClick(9) }, fontFamily = letracustom)
                CalculatorButton(text = "×", onClick = { onOperatorClick("*") }, isOperation = true, fontFamily = letracustom)
            }

            //Fila de numeros del 4 al 6
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalculatorButton(text = "4", onClick = { onNumberClick(4) }, fontFamily = letracustom)
                CalculatorButton(text = "5", onClick = { onNumberClick(5) }, fontFamily = letracustom)
                CalculatorButton(text = "6", onClick = { onNumberClick(6) }, fontFamily = letracustom)
                CalculatorButton(text = "-", onClick = { onOperatorClick("-") }, isOperation = true, fontFamily = letracustom)
            }

            //Fila de numeros del 1 al 3
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CalculatorButton(text = "1", onClick = { onNumberClick(1) }, fontFamily = letracustom)
                CalculatorButton(text = "2", onClick = { onNumberClick(2) }, fontFamily = letracustom)
                CalculatorButton(text = "3", onClick = { onNumberClick(3) }, fontFamily = letracustom)
                CalculatorButton(text = "+", onClick = { onOperatorClick("+") }, isOperation = true, fontFamily = letracustom)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Modificador del boton 0
                CalculatorButton(
                    text = "0",
                    onClick = { onNumberClick(0) },
                    fontFamily = letracustom,
                    modifier = Modifier
                        .weight(2f)
                        .aspectRatio(2f)
                )
                CalculatorButton(
                    text = ".",
                    onClick = { onDecimalClick() },
                    fontFamily = letracustom,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
                CalculatorButton(
                    text = "=",
                    onClick = { onEqualsClick() },
                    isOperation = true,
                    isEqualsButton = true,
                    fontFamily = letracustom,
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                )
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOperation: Boolean = false,
    isEqualsButton: Boolean = false,
    isAltButton: Boolean = false,
    fontFamily: FontFamily
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isEqualsButton -> Color(0xFFFFA500)
                isAltButton -> Color(0xFF7C3AED)
                isOperation -> Color(0xFF0284C7)
                else -> Color(0xFF2D2D2D)
            },
            contentColor = if (isEqualsButton) Color.White else Color(255, 255, 255, 255)
        )
    ) {
        Text(text, fontSize = 35.sp, fontFamily = fontFamily)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting()
}