package com.wl.accountbook.ui.stats

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.stats.components.StatsTopBar

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    state: StatsState,
    onAction: (StatsAction) -> Unit
) {
    StatusBarFiller()
    StatsTopBar(
        timeText = state.timeText,
        selected = state.tabIndex,
        onAction = onAction
    )
}