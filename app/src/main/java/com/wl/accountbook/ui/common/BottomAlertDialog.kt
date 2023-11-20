package com.wl.accountbook.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.TextGray

@Composable
fun BottomAlertDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    title: String = "",
    text: String = "",
    content: (@Composable () -> Unit)? = null
) {
    BottomDialog(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        onDismiss = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.DialogTitleHeight)
                    .padding(horizontal = Dimens.PaddingLarge)
            ) {
                Text(text = stringResource(id = R.string.cancel).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextGray),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onDismiss() })

                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                Text(text = stringResource(id = R.string.confirm).uppercase(),
                    style = MaterialTheme.typography.bodyMedium.copy(color = TextGray),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable {
                            onConfirm()
                            onDismiss()
                        })
            }

            HorizontalDivider()

            if (content == null) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(
                            Alignment.Start
                        )
                        .padding(vertical = Dimens.PaddingExLarge, horizontal = Dimens.PaddingXXLarge)
                )
            } else {
                content()
            }

        }
    }
}

@Preview
@Composable
fun BottomAlertDialogPreview() {
    AccountBookTheme(dynamicColor = false) {
        BottomAlertDialog(
            onDismiss = {},
            onConfirm = {},
            text = stringResource(id = R.string.record_delete_confirm)
        )
    }
}