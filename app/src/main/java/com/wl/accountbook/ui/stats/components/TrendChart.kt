package com.wl.accountbook.ui.stats.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.wl.accountbook.R
import com.wl.accountbook.extensions.toDp
import com.wl.accountbook.extensions.toPx
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LineGray
import com.wl.accountbook.ui.theme.LineLightGray
import com.wl.common.util.LogUtil
import com.wl.data.util.toActualMoney
import java.lang.Math.floor
import kotlin.math.roundToInt

@Composable
fun TrendChart(
    modifier: Modifier = Modifier,
    trendStats: List<SingleTrendStat>,
    dotRadius: Dp = 2.dp
) {

    if (trendStats.isEmpty()) return

    Box(
        modifier = Modifier
            .then(modifier)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            val color = MaterialTheme.colorScheme.primary
            val sum by remember(trendStats) {
                mutableLongStateOf(trendStats.sumOf { it.amount })
            }
            val average by remember(trendStats, sum) {
                mutableLongStateOf(sum / (trendStats.size))
            }

            Text(
                text = stringResource(id = R.string.trend_chart),
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(Dimens.PaddingMedium))
            Text(
                text = stringResource(id = R.string.all_colon, sum.toActualMoney()),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Spacer(modifier = Modifier.height(Dimens.PaddingXSmall))
            Text(
                text = stringResource(id = R.string.average_colon, average.toActualMoney()),
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Text(
                text = trendStats.maxOf { it.amount }.toActualMoney(),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.End)
            )

            var chartWidth by remember { mutableFloatStateOf(1f) }
            val spacingWidth = remember(chartWidth, trendStats) { chartWidth / (trendStats.size - 1) }

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimens.TrendChartHeight + dotRadius * 2)
                    .padding(dotRadius),
            ) {
                chartWidth = size.width - 2 * dotRadius.toPx()
                val chartHeight = Dimens.TrendChartHeight.toPx()

                val points = trendStats.mapIndexed { index, stat ->
                    Offset(
                        index.toFloat() * spacingWidth,
                        chartHeight - chartHeight * stat.percentOfMax
                    )
                }

                drawPoints(
                    points = listOf(
                        Offset(0f, 0f),
                        Offset(chartWidth, 0f),
                        Offset(0f, chartHeight * 3 / 4),
                        Offset(chartWidth, chartHeight * 3 / 4),
                        Offset(0f, chartHeight * 2 / 4),
                        Offset(chartWidth, chartHeight * 2 / 4),
                        Offset(0f, chartHeight / 4),
                        Offset(chartWidth, chartHeight / 4),
                    ),
                    pointMode = PointMode.Lines,
                    color = LineLightGray,
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawPoints(
                    points = listOf(
                        Offset(0f, chartHeight),
                        Offset(chartWidth, chartHeight),
                    ),
                    pointMode = PointMode.Lines,
                    color = LineGray,
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawPoints(
                    points = points,
                    pointMode = PointMode.Polygon,
                    color = color,
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
                drawPoints(
                    points = points,
                    pointMode = PointMode.Points,
                    color = color,
                    strokeWidth = 2 * dotRadius.toPx(),
                    cap = StrokeCap.Round
                )
            }

            val minTextWidth = MaterialTheme.typography.bodySmall.fontSize.toPx() * 2
            val interval = remember(trendStats, chartWidth) {
                kotlin.math.ceil(minTextWidth / spacingWidth).toInt()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dotRadius)
            ) {
                Row(
                    modifier = Modifier
                        .offset {
                            IntOffset(x = (0 - spacingWidth * interval / 2).toInt(), y = 0)
                        }
                ) {
                    trendStats.forEachIndexed { index, stat ->
                        if (index == trendStats.size - 1) return@forEachIndexed
                        if ((index % interval == 0 && index <= (trendStats.size - 1) - interval)) {
                            Text(
                                text = stat.label,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.width(spacingWidth.toDp() * interval),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
                Text(
                    text = trendStats.last().label,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .width(spacingWidth.toDp() * interval)
                        .align(Alignment.CenterEnd)
                        .offset {
                            IntOffset(x = (spacingWidth * interval / 2).toInt(), y = 0)
                        },
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

data class SingleTrendStat(
    val label: String,
    val amount: Long,
    val percentOfMax: Float
)

@Preview
@Composable
fun TrendChartPreview() {
    AccountBookTheme(dynamicColor = false) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TrendChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingXLarge),
                trendStats = listOf(
                    SingleTrendStat(
                        "1",
                        400,
                        1f
                    ),
                    SingleTrendStat(
                        "2",
                        200,
                        0.5f
                    ),
                    SingleTrendStat(
                        "33",
                        0,
                        0f
                    ),
                    SingleTrendStat(
                        "4",
                        200,
                        0.5f
                    )
                ),
                dotRadius = 2.dp
            )
        }
    }
}