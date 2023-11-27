package com.wl.accountbook.ui.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.Gray
import com.wl.data.util.toActualMoney
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import java.util.Date

@Composable
fun SingleRecordCard(
    record: MoneyRecordAndType,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingXSmall),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = record.type.emoji)

        Spacer(modifier = Modifier.width(Dimens.PaddingLarge))

        // type or note
        Text(
            text = record.note.ifEmpty { record.type.name },
            style = MaterialTheme.typography.bodyMedium
        )
        
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "${if (record.type.isExpenses) "-" else ""}${record.amount.toActualMoney()}",
            style = MaterialTheme.typography.bodyMedium.copy(color = Gray)
        )
    }
}

@Preview
@Composable
fun SingleRecordCardPreview() {
    AccountBookTheme() {
        SingleRecordCard(
            MoneyRecordAndType(
                20,
                MoneyRecordType(
                    1,
                    "Food",
                    "🎮",
                    true
                ),
                "",
                Date().time,
                Date().time,
            )
        )
    }
}