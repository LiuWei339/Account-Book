package com.wl.accountbook.ui.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.bottomBorder
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LineLightGray
import com.wl.accountbook.ui.theme.TextColor
import com.wl.common.util.toLocalDayString
import com.wl.domain.model.MoneyRecordAndType
import com.wl.domain.model.MoneyRecordType
import java.util.Date

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    moneyRecordAndType: MoneyRecordAndType
) {
    BoxWithConstraints(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .then(modifier)
    ) {

        val constraints = ConstraintSet {
            val category = createRefFor("category")
            val amount = createRefFor("amount")
            val date = createRefFor("date")
            val note = createRefFor("note")

            val categoryVal = createRefFor("categoryVal")
            val amountVal = createRefFor("amountVal")
            val dateVal = createRefFor("dateVal")
            val noteVal = createRefFor("noteVal")

            val barrier = createEndBarrier(category, amount, date, note)
            val padding = Dimens.PaddingMedium
            val paddingVertical = Dimens.PaddingMedium

            constrain(category) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            }
            constrain(categoryVal) {
                top.linkTo(category.top)
                bottom.linkTo(category.bottom)
                start.linkTo(barrier, padding)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(amount) {
                top.linkTo(category.bottom, paddingVertical)
                start.linkTo(parent.start)
            }
            constrain(amountVal) {
                top.linkTo(amount.top)
                bottom.linkTo(amount.bottom)
                start.linkTo(barrier, padding)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(date) {
                top.linkTo(amount.bottom, paddingVertical)
                start.linkTo(parent.start)
            }
            constrain(dateVal) {
                top.linkTo(date.top)
                bottom.linkTo(date.bottom)
                start.linkTo(barrier, padding)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }

            constrain(note) {
                top.linkTo(date.bottom, paddingVertical)
                start.linkTo(parent.start)
            }
            constrain(noteVal) {
                top.linkTo(note.top)
                bottom.linkTo(note.bottom)
                start.linkTo(barrier, padding)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        }

        ConstraintLayout(
            constraintSet = constraints,
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingExLarge)
                .padding(top = Dimens.PaddingExLarge)
                .fillMaxSize()
        ) {

            DetailField(
                stringResource(id = R.string.record_category),
                moneyRecordAndType.type.run { "$emoji $name" },
                "category",
                "categoryVal",
            )
            DetailField(
                stringResource(id = R.string.record_amount),
                moneyRecordAndType.amount.toString(),
                "amount",
                "amountVal",
            )
            DetailField(
                stringResource(id = R.string.record_date),
                Date(moneyRecordAndType.recordTime).toLocalDayString(),
                "date",
                "dateVal",
            )
            DetailField(
                stringResource(id = R.string.record_note),
                moneyRecordAndType.note,
                "note",
                "noteVal",
            )
        }
    }
}

@Composable
fun DetailField(
    fieldName: String,
    value: String,
    fieldRefId: String,
    valRefId: String
) {
    Text(
        text = fieldName,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Normal),
        textAlign = TextAlign.Start,
        modifier = Modifier.layoutId(fieldRefId)
    )

    Box(
        modifier = Modifier
            .bottomBorder(color = LineLightGray, strokeWidth = 1.dp)
            .layoutId(valRefId)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Normal,
                color = TextColor
            ),
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun DetailContentPreview() {
    AccountBookTheme(dynamicColor = false) {
        DetailContent(
            moneyRecordAndType = MoneyRecordAndType(
                20,
                MoneyRecordType(
                    1,
                    "Food",
                    "🎮",
                    true,
                    false
                ),
                "note",
                Date().time,
                Date().time,
            )
        )
    }
}