package com.wl.accountbook.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun StatusBarFiller(color: Color = MaterialTheme.colorScheme.secondary) {
    Box(
        modifier = Modifier
            .background(color = color)
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.statusBarsPadding())
    }
}
