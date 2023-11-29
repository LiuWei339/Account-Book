package com.wl.accountbook.ui.common.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wl.accountbook.ui.Dimens

@Composable
fun CommonTab(
    modifier: Modifier = Modifier,
    label: String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {

    Box(modifier = Modifier
        .bottomBorder(
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
            strokeWidth = 2.dp
        )
        .clickable {
            onClick()
        },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = Dimens.PaddingXSmall).then(modifier)
        )
    }
}