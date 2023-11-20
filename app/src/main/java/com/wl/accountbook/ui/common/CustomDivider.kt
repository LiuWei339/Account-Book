package com.wl.accountbook.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.wl.accountbook.extensions.toPx
import com.wl.accountbook.ui.theme.DivideColor

@Composable
fun HorizontalDivider(
    thickness: Float = 1f,
    color: Color = DivideColor,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .drawWithContent {
                drawRect(
                    color = color,
                    size = size.copy(height = thickness)
                )
            }
    )
}

@Composable
fun VerticalDivider(
    thickness: Float = 1f,
    color: Color = DivideColor,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(0.5.dp)
            .drawWithContent {
                drawRect(
                    color = color,
                    size = size.copy(width = thickness)
                )
            }
    )
}

@Composable
fun HorizontalDivider(
    thickness: Dp,
    color: Color = DivideColor,
    modifier: Modifier = Modifier
) {
    val thicknessInPx = thickness.toPx()
    HorizontalDivider(
        thicknessInPx,
        color,
        modifier
    )
}

@Composable
fun VerticalDivider(
    thickness: Dp,
    color: Color = DivideColor,
    modifier: Modifier = Modifier
) {
    val thicknessInPx = thickness.toPx()
    VerticalDivider(
        thicknessInPx,
        color,
        modifier
    )
}