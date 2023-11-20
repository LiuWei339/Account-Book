package com.wl.accountbook.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.BottomAlertDialog
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.TextGray

@Composable
fun DetailTopBar(
    modifier: Modifier = Modifier,
    title: String,
    onClickDelete: () -> Unit,
    onClickEdit: () -> Unit,
    onClickBack: () -> Unit
) {

    var showCancelDialog by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondary)
            .fillMaxWidth()
            .height(Dimens.HeadSize)
            .padding(horizontal = Dimens.PaddingXLarge),
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

        Spacer(modifier = Modifier.width(Dimens.PaddingMedium))

        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge.copy(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                ),
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment.Center,
                    trim = LineHeightStyle.Trim.None
                )
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.wrapContentSize()
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            painter = painterResource(id = R.drawable.ic_delete),
            tint = TextGray,
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.HeadIconSize)
                .clickable {
                    showCancelDialog = true
                }
        )

        Spacer(modifier = Modifier.width(Dimens.PaddingXLarge))

        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.HeadIconSize)
                .clickable {
                    onClickEdit()
                }
        )
    }

    if (showCancelDialog) {
        BottomAlertDialog(
            onDismiss = { showCancelDialog = false },
            onConfirm = onClickDelete,
            text = stringResource(id = R.string.record_delete_confirm)
        )
    }
}

@Preview
@Composable
fun DetailTopBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        DetailTopBar(
            title = stringResource(id = R.string.income),
            onClickBack = {},
            onClickDelete = {},
            onClickEdit = {}
        )
    }
}