package com.wl.accountbook.ui.stats.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LineLightGray
import com.wl.data.util.toActualMoney
import com.wl.domain.model.MoneyRecordType
import java.text.NumberFormat

@Composable
fun StatsTypeRanking(
    modifier: Modifier = Modifier,
    statsByType: List<TypeStats>,
    onClickType: (typeId: Int) -> Unit = {}
) {
    val sum by remember(statsByType) {
        derivedStateOf { statsByType.sumOf { it.amount } }
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.ranking),
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
            ) {
                items(
                    items = statsByType,
                    key = { it.type.id }
                ) { typeStats ->
                    SingleTypeStatDiagram(
                        typeStats = typeStats,
                        sum = sum,
                        onClick = { onClickType(typeStats.type.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun SingleTypeStatDiagram(
    modifier: Modifier = Modifier,
    typeStats: TypeStats,
    sum: Long,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.PaddingXSmall)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = typeStats.type.emoji)

        Spacer(modifier = Modifier.width(Dimens.PaddingLarge))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = typeStats.type.name,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.width(Dimens.PaddingMedium))
                Text(
                    text = NumberFormat.getPercentInstance()
                        .apply { minimumFractionDigits = 1 }.format(typeStats.amount.toFloat() / sum),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = typeStats.amount.toActualMoney(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(Dimens.PaddingXSmall))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.TypeStatsLineHeight)
                    .clip(RoundedCornerShape(Dimens.TypeStatsLineHeight / 2))
                    .background(LineLightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(typeStats.percentOfMax)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}


data class TypeStats(
    val type: MoneyRecordType,
    val amount: Long,
    val percentOfMax: Float
)

@Preview
@Composable
fun SingleTypeStatDiagramPreview() {
    AccountBookTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            SingleTypeStatDiagram(
                typeStats = TypeStats(
                    type = MoneyRecordType(
                        1,
                        "Food",
                        "🎮",
                        true
                    ),
                    amount = 3300L,
                    percentOfMax = 0.9f
                ),
                onClick = {},
                sum = 6600L
            )
        }
    }
}

@Preview
@Composable
fun StatsTypeRankingPreview() {
    AccountBookTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            StatsTypeRanking(
                statsByType = listOf(
                    TypeStats(
                        type = MoneyRecordType(
                            1,
                            "Food",
                            "🎮",
                            true
                        ),
                        amount = 3300L,
                        percentOfMax = 0.9f
                    ),
                    TypeStats(
                        type = MoneyRecordType(
                            2,
                            "Food1",
                            "🎮",
                            true
                        ),
                        amount = 1100L,
                        percentOfMax = 0.3f
                    )
                )
            )
        }
    }
}