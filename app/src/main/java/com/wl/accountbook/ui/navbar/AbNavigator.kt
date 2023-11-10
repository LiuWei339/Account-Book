package com.wl.accountbook.ui.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.StatusBarFiller
import com.wl.accountbook.ui.home.HomeScreen
import com.wl.accountbook.ui.navbar.components.BottomNavItem
import com.wl.accountbook.ui.navbar.components.BottomNavigationBar
import com.wl.accountbook.ui.navgraph.Destination
import com.wl.common.util.tomorrow
import com.wl.domain.model.MoneyRecord
import com.wl.domain.model.MoneyRecordType
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbNavigator() {

    val navItems = listOf(
        BottomNavItem(icon = R.drawable.ic_home, text = "Home"),
        BottomNavItem(icon = R.drawable.ic_statics, text = "Statics"),
        BottomNavItem(icon = R.drawable.ic_add_record, text = "Add"),
        BottomNavItem(icon = R.drawable.ic_home, text = "Home1"), // TODO
        BottomNavItem(icon = R.drawable.ic_home, text = "Home2"),
    )

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    val isBottomBarVisible = remember(backStackState) {
        // TODO
        true
    }

    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Destination.HomeDestination.route -> NavTabs.HOME.ordinal
        Destination.StaticsDestination.route -> NavTabs.STATICS.ordinal
        Destination.AddRecordDestination.route -> NavTabs.ADD_RECORD.ordinal
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(
                    items = navItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        val route = when (index) {
                            NavTabs.HOME.ordinal -> Destination.HomeDestination.route
                            NavTabs.STATICS.ordinal -> Destination.StaticsDestination.route
                            NavTabs.ADD_RECORD.ordinal -> Destination.AddRecordDestination.route
                            else -> Destination.HomeDestination.route // TODO add other tabs
                        }
                        navigateToTab(
                            navController,
                            route
                        )
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Destination.HomeDestination.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Destination.HomeDestination.route) {
                // TODO add a HomeViewModel

                HomeScreen(
                    listOf(
                        Date() to listOf(
                            MoneyRecord(
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
                            ),
                            MoneyRecord(
                                10,
                                MoneyRecordType(
                                    2,
                                    "Food1",
                                    "🏓",
                                    false
                                ),
                                "",
                                Date().time + 1,
                                Date().time + 1,
                            )
                        ),
                        Date().tomorrow() to listOf(
                            MoneyRecord(
                                20,
                                MoneyRecordType(
                                    1,
                                    "Food",
                                    "🎮",
                                    true
                                ),
                                "",
                                Date().time + 2,
                                Date().time + 2,
                            ),
                            MoneyRecord(
                                10,
                                MoneyRecordType(
                                    2,
                                    "Food1",
                                    "🏓",
                                    false
                                ),
                                "",
                                Date().time + 3,
                                Date().time + 3,
                            )
                        ),
                    )
                )
            }

            composable(route = Destination.StaticsDestination.route) {
                Column {
                    StatusBarFiller()
                    Text(
                        text = "StaticsDestination Coming soon",
                        modifier = Modifier
                            .fillMaxSize()
                            .navigationBarsPadding()
                            .background(MaterialTheme.colorScheme.secondary),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            composable(route = Destination.AddRecordDestination.route) {
                Text(text = "AddRecordDestination Coming soon")
            }
        }
    }
}

enum class NavTabs {
    HOME, STATICS, ADD_RECORD
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}