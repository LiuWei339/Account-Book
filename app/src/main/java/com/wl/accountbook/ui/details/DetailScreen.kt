package com.wl.accountbook.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.NavigationBarFiller
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.details.components.DetailContent
import com.wl.accountbook.ui.details.components.DetailTopBar
import com.wl.domain.model.MoneyRecordAndType

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    moneyRecordAndType: MoneyRecordAndType,
    onClickDelete: () -> Unit = {},
    onClickEdit: () -> Unit = {},
    navigateUp: () -> Unit = {}
) {

    Column(modifier = modifier) {
        StatusBarFiller()
        DetailTopBar(
            title = if (moneyRecordAndType.type.isExpenses) pluralStringResource(
                id = R.plurals.expense,
                count = 1
            ) else stringResource(id = R.string.income),
            onClickDelete = {
                onClickDelete()
                navigateUp()
            },
            onClickEdit = onClickEdit,
            onClickBack = navigateUp
        )
        DetailContent(moneyRecordAndType = moneyRecordAndType)
        NavigationBarFiller()
    }
}

