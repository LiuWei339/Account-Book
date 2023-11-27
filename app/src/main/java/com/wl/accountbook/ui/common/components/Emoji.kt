package com.wl.accountbook.ui.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.wl.accountbook.extensions.toSp

@Composable
fun Emoji(
    emoji: String,
    size: Dp = Dp.Unspecified,
    modifier: Modifier = Modifier
) {

    val sizeInSp: TextUnit = size.toSp()

    Text(
        text = emoji,
        modifier = modifier,
        fontSize = sizeInSp
    )
}