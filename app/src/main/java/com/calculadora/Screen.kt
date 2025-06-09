package com.calculadora

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.calculadora.logic.CalculatorLogic
import androidx.compose.ui.text.style.TextAlign

@Composable
fun CalculatorScreen() {
    val logic = remember { CalculatorLogic() }
    var modoclaro by remember { mutableStateOf(true) }

    val buttonList = listOf(
        ButtonData("C", { logic.clear() }, isOperation = true),
        ButtonData("±", { logic.signo() }, isOperation = true),
        ButtonData("%", { logic.operacion("%") }, isOperation = true),
        ButtonData("÷", { logic.operacion("/") }, isOperation = true),

        ButtonData("7", { logic.numero(7) }),
        ButtonData("8", { logic.numero(8) }),
        ButtonData("9", { logic.numero(9) }),
        ButtonData("×", { logic.operacion("*") }, isOperation = true),

        ButtonData("4", { logic.numero(4) }),
        ButtonData("5", { logic.numero(5) }),
        ButtonData("6", { logic.numero(6) }),
        ButtonData("-", { logic.operacion("-") }, isOperation = true),

        ButtonData("1", { logic.numero(1) }),
        ButtonData("2", { logic.numero(2) }),
        ButtonData("3", { logic.numero(3) }),
        ButtonData("+", { logic.operacion("+") }, isOperation = true),

        ButtonData("0", { logic.numero(0) }, span = 2),
        ButtonData(".", { logic.decimal() }),
        ButtonData("=", { logic.igual() }, isOperation = true, isEquals = true)
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = if (modoclaro) Color(45, 45, 45) else Color(230, 230, 230)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = logic.displayValue,
                fontSize = 80.sp,
                color = if (modoclaro) Color.White else Color.Black,
                fontFamily = FontFamily.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                textAlign = TextAlign.End
            )

            Button(
                onClick = { modoclaro = !modoclaro },
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (modoclaro) Color(66, 66, 66) else Color(200, 200, 200),
                    contentColor = if (modoclaro) Color.White else Color.Black
                )
            ) {
                Text("Modo Oscuro")
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    items = buttonList,
                    span = { GridItemSpan(it.span) }
                ) { item ->
                    CalculatorButton(
                        text = item.text,
                        onClick = item.onClick,
                        isOperation = item.isOperation,
                        isEqualsButton = item.isEquals,
                        modoclaro = modoclaro,
                        fontFamily = FontFamily.Default,
                        modifier = Modifier
                            .aspectRatio(if (item.span == 2) 2f else 1f)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}
