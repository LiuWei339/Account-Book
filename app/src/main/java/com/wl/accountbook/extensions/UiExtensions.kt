package com.wl.accountbook.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@Composable
fun Dp.toSp(): TextUnit {
    return with(LocalDensity.current) { this@toSp.toSp() }
}

@Composable
fun TextUnit.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}

@Composable
fun TextUnit.toPx(): Float {
    return with(LocalDensity.current) { this@toPx.toPx() }
}

@Composable
fun Float.toDp(): Dp {
    return with(LocalDensity.current) { this@toDp.toDp() }
}
