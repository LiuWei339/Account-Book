package com.wl.accountbook.ui.navbar.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.wl.accountbook.R
import com.wl.accountbook.ui.Dimens
import com.wl.accountbook.ui.common.NavigationBarFiller
import com.wl.accountbook.ui.theme.AccountBookTheme
import com.wl.accountbook.ui.theme.LightGrayBg

@Composable
fun BottomNavigationBar(
    items: List<BottomNavItem>, selected: Int, onItemClick: (Int) -> Unit
) {
    val middleIndex = remember(items) {
        items.size / 2
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightGrayBg)
                    .align(Alignment.BottomCenter),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                items.forEachIndexed { index, item ->
                    if (index != middleIndex) {
                        BottomNavigationBarItem(
                            item,
                            index == selected,
                            onClick = {
                                onItemClick(index)
                            }
                        )
                    } else {
                        Box(modifier = Modifier.width(Dimens.NavBigIconSize))
                    }
                }
            }

            BigBottomNavigationBarItem(
                items[middleIndex],
                modifier = Modifier
                    .align(Alignment.TopCenter),
                onClick = { onItemClick(middleIndex) }
            )
        }

        NavigationBarFiller(color = LightGrayBg)
    }

}

@Composable
fun BigBottomNavigationBarItem(
    item: BottomNavItem,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(bottom = Dimens.PaddingLarge)
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier.size(Dimens.NavBigIconSize),
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
        Text(
            text = stringResource(item.label), style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun BottomNavigationBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = Dimens.PaddingLarge)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onClick()
            }
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier.size(Dimens.NavIconSize),
            tint = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
        Text(
            text = stringResource(id = item.label), style = MaterialTheme.typography.labelSmall,
            color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onBackground
        )
    }
}

data class BottomNavItem(
    @DrawableRes val icon: Int, @StringRes val label: Int
)

@Preview
@Composable
fun BottomNavigationBarPreview() {
    AccountBookTheme(dynamicColor = false) {
        BottomNavigationBar(items = listOf(
            BottomNavItem(icon = R.drawable.ic_home, label = R.string.record),
            BottomNavItem(icon = R.drawable.ic_statics, label = R.string.stats),
            BottomNavItem(icon = R.drawable.ic_add_record, label = R.string.add),
            BottomNavItem(icon = R.drawable.ic_home, label = R.string.add),
            BottomNavItem(icon = R.drawable.ic_home, label = R.string.add),
        ), 0, {})
    }
}