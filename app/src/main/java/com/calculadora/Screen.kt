package com.calculadora

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.DarkMode
import androidx.compose.material.icons.twotone.LightMode
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

val letracustom = FontFamily(
    Font(R.font.rubik, FontWeight.Normal),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    val logic = remember { CalculatorLogic() }
    var modoclaro by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                actions = {
                    IconButton(onClick = { modoclaro = !modoclaro }) {
                        Icon(
                            imageVector = if (modoclaro) Icons.TwoTone.DarkMode else Icons.TwoTone.LightMode,
                            contentDescription = "Modo Oscuro",
                            tint = if (modoclaro) Color.White else Color.Black
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = if (modoclaro) Color(45, 45, 45) else Color(230, 230, 230)
                )
            )
        },
        containerColor = if (modoclaro) Color(45, 45, 45) else Color(230, 230, 230)
    ) { paddingValues ->

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val isLandscape = this.maxWidth > this.maxHeight

            val buttonList = if (isLandscape) {
                listOf(
                    ButtonData("C", { logic.clear() }, isOperation = true),
                    ButtonData("7", { logic.numero(7) }),
                    ButtonData("8", { logic.numero(8) }),
                    ButtonData("9", { logic.numero(9) }),
                    ButtonData("÷", { logic.operacion("/") }, isOperation = true),

                    ButtonData("±", { logic.signo() }, isOperation = true),
                    ButtonData("4", { logic.numero(4) }),
                    ButtonData("5", { logic.numero(5) }),
                    ButtonData("6", { logic.numero(6) }),
                    ButtonData("×", { logic.operacion("*") }, isOperation = true),

                    ButtonData("%", { logic.operacion("%") }, isOperation = true),
                    ButtonData("1", { logic.numero(1) }),
                    ButtonData("2", { logic.numero(2) }),
                    ButtonData("3", { logic.numero(3) }),
                    ButtonData("-", { logic.operacion("-") }, isOperation = true),

                    ButtonData(".", { logic.decimal() }),
                    ButtonData("0", { logic.numero(0) }, span = 2),
                    ButtonData("=", { logic.igual() }, isOperation = true, isEquals = true),
                    ButtonData("+", { logic.operacion("+") }, isOperation = true),
                )
            } else {
                listOf(
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
            }

            if (isLandscape) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = logic.operationText,
                            fontSize = 24.sp,
                            fontFamily = letracustom,
                            color = if (modoclaro) Color.LightGray else Color.DarkGray,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = logic.displayValue,
                            fontSize = 49.sp,
                            fontFamily = letracustom,
                            color = if (modoclaro) Color.White else Color.Black,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(buttonList, span = { GridItemSpan(it.span) }) { item ->
                            CalculatorButton(
                                text = item.text,
                                onClick = item.onClick,
                                isOperation = item.isOperation,
                                isEqualsButton = item.isEquals,
                                modoclaro = modoclaro,
                                fontFamily = letracustom,
                                isLandscape = true,
                                modifier = Modifier
                                    .aspectRatio(if (item.span == 2) 2f else 1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = logic.operationText,
                            fontSize = 26.sp,
                            fontFamily = letracustom,
                            color = if (modoclaro) Color.LightGray else Color.DarkGray,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = logic.displayValue,
                            fontSize = 80.sp,
                            fontFamily = letracustom,
                            color = if (modoclaro) Color.White else Color.Black,
                            textAlign = TextAlign.End,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(4),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(buttonList, span = { GridItemSpan(it.span) }) { item ->
                            CalculatorButton(
                                text = item.text,
                                onClick = item.onClick,
                                isOperation = item.isOperation,
                                isEqualsButton = item.isEquals,
                                modoclaro = modoclaro,
                                fontFamily = letracustom,
                                isLandscape = false,
                                modifier = Modifier
                                    .aspectRatio(if (item.span == 2) 2f else 1f)
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}
