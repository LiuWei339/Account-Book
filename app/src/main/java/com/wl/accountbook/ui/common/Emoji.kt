package com.wl.accountbook.ui.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Emoji(
    emoji: String,
    size: Dp = Dp.Unspecified,
    modifier: Modifier = Modifier
) {

    val sizeInSp: TextUnit = with(LocalDensity.current) {
        size.toSp()
    }

    Text(
        text = emoji,
        modifier = modifier,
        fontSize = sizeInSp
    )
}