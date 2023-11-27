package com.wl.accountbook.ui.search.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    searchText: String,
    onClickBack: () -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(horizontal = Dimens.PaddingXLarge)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_left_arrow),
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.HeadIconSize)
                .clickable {
                    onClickBack()
                }
        )

        Spacer(modifier = Modifier.width(Dimens.PaddingXLarge))

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            text = searchText,
            readOnly = false,
            onValueChange = onValueChange,
            onSearch = onSearch
        )
    }
}

@Preview
@Composable
fun SearchTopBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        SearchTopBar(
            searchText = "test",
            onClickBack = {},
            onValueChange = {},
            onSearch = {},
        )
    }
}