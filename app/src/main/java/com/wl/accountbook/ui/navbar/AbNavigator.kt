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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.wl.accountbook.R
import com.wl.accountbook.ui.common.components.StatusBarFiller
import com.wl.accountbook.ui.details.detailScreen
import com.wl.accountbook.ui.details.navigateToDetail
import com.wl.accountbook.ui.home.HomeScreen
import com.wl.accountbook.ui.home.HomeViewModel
import com.wl.accountbook.ui.navbar.components.BottomNavItem
import com.wl.accountbook.ui.navbar.components.BottomNavigationBar
import com.wl.accountbook.ui.navgraph.Destination
import com.wl.accountbook.ui.record.recordEditScreen
import com.wl.accountbook.ui.search.navigateToSearch
import com.wl.accountbook.ui.search.searchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbNavigator() {

    val navItems = remember {
        listOf(
            BottomNavItem(
                icon = R.drawable.ic_home,
                label = R.string.record,
                route = Destination.HomeDestination.route
            ),
            BottomNavItem(
                icon = R.drawable.ic_add_record,
                label = R.string.add,
                route = Destination.RecordEditDestination.withArgs("-1")
            ),
            BottomNavItem(
                icon = R.drawable.ic_statics,
                label = R.string.stats,
                route = Destination.StaticsDestination.route
            ),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    val isBottomBarVisible = remember(backStackState) {
        (backStackState?.destination?.route ?: "") in listOf(
            Destination.HomeDestination.route,
            Destination.StaticsDestination.route
        )
    }

    var selectedRoute by rememberSaveable {
        mutableStateOf(navItems[0].route)
    }
    selectedRoute = backStackState?.destination?.route ?: ""

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigationBar(
                    items = navItems,
                    selectedRoute = selectedRoute,
                    onItemClick = { route ->
                        navController.navigateToTab(route)
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
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.stateFlow.collectAsState()
                HomeScreen(
                    state = state.value,
                    onAction = viewModel::onAction,
                    navigateToDetail = navController::navigateToDetail,
                    navigateToSearch = navController::navigateToSearch
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

            recordEditScreen(navController)
            detailScreen(navController)
            searchScreen(navController)
        }
    }
}

fun NavController.navigateToTab(route: String) {
    navigate(route) {
        graph.startDestinationRoute?.let {
            popUpTo(it) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

