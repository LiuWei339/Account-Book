package com.wl.accountbook.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.OnLifecycleEvent
import com.wl.accountbook.ui.common.components.NavigationBarFiller
import com.wl.accountbook.ui.common.components.SingleRecordCard
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.search.components.SearchTopBar

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    onClickBack: () -> Unit,
    navigateToDetail: (createTime: Long) -> Unit
) {

    Column {
        StatusBarFiller()
        SearchTopBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = state.searchText,
            onClickBack = onClickBack,
            onValueChange = { onAction(SearchAction.ChangeSearchText(it)) },
            onSearch = { onAction(SearchAction.Search) },
        )

        Column(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            state.records.forEach { record ->
                SingleRecordCard(
                    record,
                    modifier = Modifier
                        .padding(horizontal = Dimens.PaddingLarge)
                        .clickable {
                            navigateToDetail(record.createTime)
                        }
                )
            }
        }
        NavigationBarFiller()
    }

    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_CREATE) {
            onAction(SearchAction.CREATE)
        }
    }
}