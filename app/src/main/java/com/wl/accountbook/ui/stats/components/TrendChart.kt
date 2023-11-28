package com.wl.accountbook.ui.stats.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.drawText
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LineGray
import com.wl.accountbook.ui.theme.LineLightGray

@Composable
fun TrendChart(
    modifier: Modifier = Modifier,
    trendStats: List<SingleTrendStat>,
    dotRadius: Dp = 2.dp
) {

    Box(
        modifier = Modifier
            .height(Dimens.TrendChartHeight)
            .then(modifier)
    ) {
        val color = MaterialTheme.colorScheme.primary
        var size by remember {
            mutableStateOf(IntSize.Zero)
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size = it }
                .padding(dotRadius),
        ) {
            val chartWidth = size.width - 2 * dotRadius.toPx()
            val chartHeight = size.height - 2 * dotRadius.toPx()
            val spacingWidth = chartWidth / (trendStats.size - 1)

            val points = trendStats.mapIndexed { index, stat ->
                Offset(index.toFloat() * spacingWidth, chartHeight - chartHeight * stat.percentOfMax)
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
                    Offset(chartWidth, chartHeight/ 4),
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
    }

}

data class SingleTrendStat(
    val label: String,
    val amount: String,
    val percentOfMax: Float
)

@Preview
@Composable
fun TrendChartPreview() {
    AccountBookTheme(dynamicColor = false) {
        Box(modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            TrendChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.PaddingXLarge),
                trendStats = listOf(
                    SingleTrendStat(
                        "1",
                        "4",
                        1f
                    ),
                    SingleTrendStat(
                        "2",
                        "2",
                        0.5f
                    ),
                    SingleTrendStat(
                        "3",
                        "0",
                        0f
                    ),
                    SingleTrendStat(
                        "4",
                        "2",
                        0.5f
                    )
                ),
                dotRadius = 2.dp
            )
        }
    }
}