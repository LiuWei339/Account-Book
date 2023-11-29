package com.wl.accountbook.ui.home

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wl.accountbook.ui.details.navigateToDetail
import com.wl.accountbook.ui.navbar.navigateToTab
import com.wl.accountbook.ui.navgraph.Destination
import com.wl.accountbook.ui.search.navigateToSearch

fun NavGraphBuilder.homeScreen(navController: NavHostController) {
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
}

fun NavController.navigateToHome() {
    navigateToTab(Destination.HomeDestination.route)
}