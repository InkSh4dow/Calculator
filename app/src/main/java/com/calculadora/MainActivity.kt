package com.calculadora

import android.os.Bundle
import java.util.Locale
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

//Letra custom
val letracustom = FontFamily(
    Font(R.font.rubik, FontWeight.Normal),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Greeting() }
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
                result = if (num2 != 0.0) num1 / num2 else {
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

        result?.let {
            displayValue = if (it == it.toLong().toDouble()) {
                it.toLong().toString()
            } else {
                String.format(Locale.US, "%.6f", it).toDouble().toString()
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

    fun onToggleSignClick() {
        if (displayValue != "0" && displayValue != "Error") {
            displayValue = if (displayValue.startsWith("-")) {
                displayValue.removePrefix("-")
            } else {
                "-$displayValue"
            }
        }
    }

    //Funcion de los botones (De arriba a abajo y de izq a derecha)
    val buttonList = listOf(
        ButtonData("C", { onClearClick() }, isOperation = true),
        ButtonData("±", { onToggleSignClick() }, isOperation = true),
        ButtonData("%", { onOperatorClick("%") }, isOperation = true),
        ButtonData("÷", { onOperatorClick("/") }, isOperation = true),

        ButtonData("7", { onNumberClick(7) }),
        ButtonData("8", { onNumberClick(8) }),
        ButtonData("9", { onNumberClick(9) }),
        ButtonData("×", { onOperatorClick("*") }, isOperation = true),

        ButtonData("4", { onNumberClick(4) }),
        ButtonData("5", { onNumberClick(5) }),
        ButtonData("6", { onNumberClick(6) }),
        ButtonData("-", { onOperatorClick("-") }, isOperation = true),

        ButtonData("1", { onNumberClick(1) }),
        ButtonData("2", { onNumberClick(2) }),
        ButtonData("3", { onNumberClick(3) }),
        ButtonData("+", { onOperatorClick("+") }, isOperation = true),

        ButtonData("0", { onNumberClick(0) }, span = 2),
        ButtonData(".", { onDecimalClick() }),
        ButtonData("=", { onEqualsClick() }, isOperation = true, isEquals = true)
    )

    //Interfaz
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(45, 45, 45)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                text = displayValue,
                fontSize = 80.sp,
                color = Color.White,
                fontFamily = letracustom,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .wrapContentHeight(Alignment.Bottom),
                textAlign = TextAlign.End
            )

            // Organizacion de los botones
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            )
            {
                items(
                    items = buttonList,
                    span = { item -> GridItemSpan(item.span)
                    })
            { item ->
                CalculatorButton(
                    text = item.text,
                    onClick = item.onClick,
                    isOperation = item.isOperation,
                    isEqualsButton = item.isEquals,
                    fontFamily = letracustom,
                    modifier = Modifier
                        .aspectRatio(if (item.span == 2) 2f else 1f)
                        .fillMaxWidth()
                )
                }
            }
        }
    }
}


data class ButtonData(
    val text: String,
    val onClick: () -> Unit,
    val isOperation: Boolean = false,
    val isEquals: Boolean = false,
    val span: Int = 1
)

@Composable
// Diseño de los botones
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOperation: Boolean = false,
    isEqualsButton: Boolean = false,
    fontFamily: FontFamily
) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = when {
                isEqualsButton -> Color(255, 165, 0)
                isOperation -> Color(3, 169, 244)
                else -> Color(66, 66, 66)
            },
            contentColor = Color.White
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
