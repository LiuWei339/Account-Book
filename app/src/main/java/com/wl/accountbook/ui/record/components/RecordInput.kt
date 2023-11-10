package com.wl.accountbook.ui.record.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.AutoSizeText
import com.wl.accountbook.ui.common.HorizontalDivider
import com.wl.accountbook.ui.record.calculator.CalculatorAction
import com.wl.accountbook.ui.record.calculator.CalculatorOperation
import com.wl.accountbook.ui.record.calculator.CalculatorState
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LightGrayBg

private const val MAX_SHOW_LENGTH = 15

@Composable
fun RecordInput(
    modifier: Modifier = Modifier,
    showTime: String = "",
    note: String = "",
    calcState: CalculatorState,
    onCalcAction: (CalculatorAction) -> Unit = {},
    onClickDate: () -> Unit = {},
    onChangeNote: (String) -> Unit = {}
) {

    var calText = remember(calcState) {
        val sb = StringBuilder().apply {
            append(calcState.number1.ifEmpty { "0" })
            if (calcState.number1HasDecimal) {
                append(".")
                append(calcState.number1Decimal)
            }
            if (calcState.operation != null) {
                when (calcState.operation) {
                    CalculatorOperation.Add -> append("+")
                    CalculatorOperation.Subtract -> append("-")
                }
                append(calcState.number2)
                if (calcState.number2HasDecimal) {
                    if (calcState.number2.isEmpty()) append("0")
                    append(".")
                    append(calcState.number2Decimal)
                }
            }
        }
        sb.toString().takeLast(MAX_SHOW_LENGTH)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.PaddingMedium)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MaterialTheme.colorScheme.background, LightGrayBg)
                    )
                )
        )

        Text(
            text = calText,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(
                    horizontal = Dimens.PaddingXLarge, vertical = Dimens.PaddingSmall
                )
                .fillMaxWidth(),
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.PaddingMedium, vertical = Dimens.PaddingXSmall)
                .background(LightGrayBg), verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Note:",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(
                    start = Dimens.PaddingXXLarge, end = Dimens.PaddingSmall,
                    top = Dimens.PaddingSmall, bottom = Dimens.PaddingSmall
                )
            )

            BasicTextField(
                value = note,
                onValueChange = {
                    onChangeNote(it)
                },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        end = Dimens.PaddingXLarge,
                        top = Dimens.PaddingXSmall,
                        bottom = Dimens.PaddingXSmall
                    ),
                textStyle = MaterialTheme.typography.bodyMedium
            )
        }

        Keyboard(
            showTime,
            onClickDate,
            onCalcAction
        )
    }
}

@Composable
private fun Keyboard(
    showTime: String,
    onSelectDate: () -> Unit,
    onAction: (CalculatorAction) -> Unit
) {

    val keyBoardItems = listOf(
        KeyboardItem("1", CalculatorAction.Number(1)),
        KeyboardItem("2", CalculatorAction.Number(2)),
        KeyboardItem("3", CalculatorAction.Number(3)),
        KeyboardItem("", CalculatorAction.Delete),
        KeyboardItem("4", CalculatorAction.Number(4)),
        KeyboardItem("5", CalculatorAction.Number(5)),
        KeyboardItem("6", CalculatorAction.Number(6)),
        KeyboardItem("+", CalculatorAction.Operation(CalculatorOperation.Add)),
        KeyboardItem("7", CalculatorAction.Number(7)),
        KeyboardItem("8", CalculatorAction.Number(8)),
        KeyboardItem("9", CalculatorAction.Number(9)),
        KeyboardItem("-", CalculatorAction.Operation(CalculatorOperation.Subtract)),
        KeyboardItem(".", CalculatorAction.Decimal),
        KeyboardItem("0", CalculatorAction.Number(0)),
        KeyboardItem("", CalculatorAction.Delete),
        KeyboardItem("Done", CalculatorAction.Calculate),
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightGrayBg),
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        HorizontalDivider(1.dp, color = LightGrayBg)
        for (i in keyBoardItems.indices step 4) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                for (j in 0 until 4) {
                    when {
                        i + j == 3 -> KeyBoardKey( // Timer selector
                            "",
                            onClick = {
                                onSelectDate()
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            AutoSizeText(
                                text = showTime,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        i + j == 14 -> { // DEL button
                            KeyBoardKey(
                                key = keyBoardItems[14].symbol,
                                onClick = { onAction(keyBoardItems[14].action) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(Dimens.DeleteIconWidth)
                                        .aspectRatio(1.6f),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        i + j == 15 -> { // Calc button
                            KeyBoardKey(
                                key = keyBoardItems[15].symbol,
                                onClick = { onAction(keyBoardItems[15].action) },
                                modifier = Modifier
                                    .weight(1f)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                        }

                        else -> KeyBoardKey(
                            key = keyBoardItems[i + j].symbol,
                            onClick = { onAction(keyBoardItems[i + j].action) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

private data class KeyboardItem(
    val symbol: String,
    val action: CalculatorAction
)

@Composable
private fun KeyBoardKey(
    key: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {
        Text(
            text = key, style = MaterialTheme.typography.bodyLarge
        )
    }
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .aspectRatio(1.4f)
            .background(MaterialTheme.colorScheme.background)
            .then(modifier),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@Preview
@Composable
fun RecordInputPreview() {
    AccountBookTheme(dynamicColor = false) {
        RecordInput(
            calcState = CalculatorState(
                number1 = "",
                number1HasDecimal = true,
                number1Decimal = "2",
                operation = CalculatorOperation.Subtract,
                number2 = "2"
            ),
        )
    }
}