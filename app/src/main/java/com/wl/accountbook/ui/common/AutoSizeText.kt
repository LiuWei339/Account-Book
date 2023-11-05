package com.wl.accountbook.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.isUnspecified

@Composable
fun AutoSizeText(
    text: String,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    modifier: Modifier = Modifier,
    color: Color = style.color
) {

    var resizedTextStyle by remember {
        mutableStateOf(style)
    }

    var defaultFontSize = MaterialTheme.typography.bodySmall.fontSize

    var readyToDraw by remember {
        mutableStateOf(false)
    }

    Text(
        text = text,
        style = resizedTextStyle,
        color = color,
        modifier = modifier.drawWithContent {
            if (readyToDraw) {
                drawContent()
            }
        },
        softWrap = false,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                if (style.fontSize.isUnspecified) {
                    resizedTextStyle = resizedTextStyle.copy(
                        fontSize = defaultFontSize
                    )
                }
                resizedTextStyle = resizedTextStyle.copy(
                    fontSize = resizedTextStyle.fontSize * 0.95
                )
            } else {
                readyToDraw = true
            }
        }
    )
}