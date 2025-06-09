package com.calculadora

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ButtonData(
    val text: String,
    val onClick: () -> Unit,
    val isOperation: Boolean = false,
    val isEquals: Boolean = false,
    val span: Int = 1
)

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isOperation: Boolean = false,
    isEqualsButton: Boolean = false,
    modoclaro: Boolean,
    fontFamily: FontFamily,
    isLandscape: Boolean

) {
    Button(
        onClick = onClick,
        modifier = modifier.padding(4.dp),
        shape = androidx.compose.foundation.shape.CircleShape,
        colors = if (modoclaro) {
            ButtonDefaults.buttonColors(
                containerColor = when {
                    isEqualsButton -> Color(255, 165, 0)
                    isOperation -> Color(3, 169, 244)
                    else -> Color(66, 66, 66)
                },
                contentColor = Color.White
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = when {
                    isEqualsButton -> Color(255, 165, 0)
                    isOperation -> Color(3, 169, 244)
                    else -> Color.White
                },
                contentColor = Color.Black
            )
        }
    ) {
        Text(
            text = text,
            fontSize = if (isLandscape) 18.sp else 35.sp,
            fontFamily = fontFamily
        )
    }
}

