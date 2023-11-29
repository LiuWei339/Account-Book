package com.wl.accountbook.ui.stats

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wl.accountbook.ui.navbar.navigateToTab
import com.wl.accountbook.ui.navgraph.Destination

fun NavGraphBuilder.statsScreen(navController: NavHostController) {
    composable(
        route = Destination.StatsDestination.route
    ) {
        val viewModel: StatsViewModel = hiltViewModel()
        StatsScreen(
            state = viewModel.state,
            onAction = viewModel::onAction,
        )
    }
}

fun NavController.navigateToStats() {
    navigateToTab(Destination.StatsDestination.route)
}