package com.wl.accountbook.data

import android.content.Context
import androidx.annotation.StringRes
import com.wl.accountbook.R
import com.wl.domain.model.MoneyRecordType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * preset record types, id of these types should start from 1000
 */
class PresetRecordTypes @Inject constructor(
    @ApplicationContext private val context: Context
) {
    operator fun invoke(): List<MoneyRecordType> {
        return listOf(
            // expenses
            buildPresetRecordTypes(1000, R.string.record_type_food, "🍽️", true),
            buildPresetRecordTypes(1001, R.string.record_type_household, "🧻", true),
            buildPresetRecordTypes(1002, R.string.record_type_transport, "🚗", true),
            buildPresetRecordTypes(1003, R.string.record_type_housing, "🏠", true),
            buildPresetRecordTypes(1004, R.string.record_type_pets, "🐶", true),
            buildPresetRecordTypes(1005, R.string.record_type_entertainment, "🎤", true),
            buildPresetRecordTypes(1006, R.string.record_type_travel, "🧳", true),
            buildPresetRecordTypes(1007, R.string.record_type_health, "🏥", true),
            buildPresetRecordTypes(1008, R.string.record_type_education, "📖", true),
            buildPresetRecordTypes(1009, R.string.record_type_gifts, "🎁", true),
            buildPresetRecordTypes(1010, R.string.record_type_shopping, "🛒", true),
            buildPresetRecordTypes(1011, R.string.record_type_apparel, "👕", true),
            buildPresetRecordTypes(1012, R.string.record_type_exchange, "💱", true),
            buildPresetRecordTypes(1013, R.string.record_type_other, "⭐", true),

            // income
            buildPresetRecordTypes(1100, R.string.record_type_salary, "💳", false),
            buildPresetRecordTypes(1101, R.string.record_type_allowance, "💸", false),
            buildPresetRecordTypes(1102, R.string.record_type_bonus, "🏅", false),
            buildPresetRecordTypes(1103, R.string.record_type_part_time, "🕔", false),
            buildPresetRecordTypes(1104, R.string.record_type_investment, "📈", false),
            buildPresetRecordTypes(1105, R.string.record_type_exchange, "💱", false),
            buildPresetRecordTypes(1106, R.string.record_type_other, "⭐", false),
        )
    }

    private fun buildPresetRecordTypes(
        id: Int = 0,
        @StringRes nameResId: Int,
        emoji: String,
        isExpenses: Boolean,
    ): MoneyRecordType {
        return MoneyRecordType(
            id,
            context.getString(nameResId),
            emoji,
            isExpenses,
            true
        )
    }
}