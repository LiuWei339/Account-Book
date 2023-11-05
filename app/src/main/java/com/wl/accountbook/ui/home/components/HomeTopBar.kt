package com.wl.accountbook.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme

@Composable
fun HomeTopBar(
    date: String,
    onDateClick: () -> Unit,
    onSearchClick: () -> Unit
) {
    val dateText by rememberSaveable {
        mutableStateOf(date)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.HeadSize)
            .background(color = MaterialTheme.colorScheme.secondary)
            .padding(horizontal = Dimens.PaddingXXXLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = null,
            modifier = Modifier.size(Dimens.HeadIconSize)
        )

        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = dateText,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(Dimens.PaddingXSmall),
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = R.drawable.ic_down_arrow),
                contentDescription = null,
                modifier = Modifier.size(Dimens.HeadIconSize)
            )
        }

        Image(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = null,
            modifier = Modifier.size(Dimens.HeadIconSize)
        )
    }
}

@Preview
@Composable
fun HomeTopBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        HomeTopBar("Oct 2023", {}, {})
    }
}