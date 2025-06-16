package com.calculadora

import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class CalculatorLogic {
    var operationText by mutableStateOf("")
        private set
    var displayValue by mutableStateOf("0")
        private set
    var currentNumber: Double? = null
    var previousNumber: Double? = null
    var currentOperation: String? = null
    var newCalculationStarted: Boolean = false

    fun numero(number: Int) {
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

    fun decimal() {
        if (newCalculationStarted) {
            displayValue = "0."
            newCalculationStarted = false
        } else if (!displayValue.contains(".")) {
            displayValue += "."
        }
    }

    fun operacion(operation: String) {
        currentNumber = displayValue.toDoubleOrNull()
        if (currentNumber != null) {
            previousNumber = currentNumber
            currentOperation = operation
            operationText = "${displayValue} $operation"
            newCalculationStarted = true
        }
    }

    fun igual() {
        val num1 = previousNumber ?: return
        val num2 = displayValue.toDoubleOrNull() ?: return
        var result: Double? = null

        when (currentOperation) {
            "+" -> result = num1 + num2
            "-" -> result = num1 - num2
            "*" -> result = num1 * num2
            "/" -> {
                result = if (num2 != 0.0) num1 / num2 else {
                    clear()
                    displayValue = "Error"
                    return
                }
            }
            "%" -> result = num1 * (num2 / 100.0)
        }

        result?.let {
            val num1Text = if (num1 % 1.0 == 0.0) num1.toInt().toString() else num1.toString()
            val num2Text = if (num2 % 1.0 == 0.0) num2.toInt().toString() else num2.toString()
            operationText = "$num1Text $currentOperation $num2Text"
            displayValue = if (it % 1.0 == 0.0) {
                it.toInt().toString()
            } else {
                it.toString().trimEnd('0').trimEnd('.')
            }
            currentNumber = result
            previousNumber = null
            currentOperation = null
            newCalculationStarted = true
        }
    }

    fun clear() {
        displayValue = "0"
        currentNumber = null
        previousNumber = null
        currentOperation = null
        newCalculationStarted = false
    }

    fun signo() {
        if (displayValue != "0" && displayValue != "Error") {
            displayValue = if (displayValue.startsWith("-")) {
                displayValue.removePrefix("-")
            } else {
                "-$displayValue"
            }
        }
    }
}
