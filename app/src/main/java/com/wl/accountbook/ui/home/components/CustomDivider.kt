package com.wl.accountbook.ui.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.unit.dp
import com.wl.accountbook.ui.theme.DivideColor

@Composable
fun HorizontalDivider(thickness: Float = 1f, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(0.5.dp)
            .drawWithContent {
                drawRect(
                    color = DivideColor,
                    size = size.copy(height = thickness)
                )
            }
    )
}

@Composable
fun VerticalDivider(thickness: Float = 1f, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(0.5.dp)
            .drawWithContent {
                drawRect(
                    color = DivideColor,
                    size = size.copy(width = thickness)
                )
            }
    )
}