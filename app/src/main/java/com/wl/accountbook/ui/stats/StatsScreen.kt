package com.wl.accountbook.ui.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.OnLifecycleEvent
import com.wl.accountbook.ui.common.components.NavigationBarFiller
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.stats.components.StatsTopBar
import com.wl.accountbook.ui.stats.components.StatsTypeRanking
import com.wl.accountbook.ui.stats.components.TrendChart

@Composable
fun StatsScreen(
    modifier: Modifier = Modifier,
    state: StatsState,
    onAction: (StatsAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        StatusBarFiller()
        StatsTopBar(
            timeStamp = state.timeStamp,
            isShowMonthStats = state.isShowMonthStats,
            selected = state.tabIndex,
            onAction = onAction
        )
        TrendChart(
            modifier = Modifier.padding(Dimens.PaddingXLarge),
            trendStats = state.trendStats
        )
        StatsTypeRanking(
            modifier = Modifier.padding(Dimens.PaddingXLarge),
            statsByType = state.statsByType
        )
        NavigationBarFiller()

        OnLifecycleEvent { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                onAction(StatsAction.CREATE)
            }
        }
    }
}