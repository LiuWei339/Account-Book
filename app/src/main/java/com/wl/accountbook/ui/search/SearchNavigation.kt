package com.wl.accountbook.ui.search

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.wl.accountbook.ui.details.navigateToDetail
import com.wl.accountbook.ui.navgraph.Destination

fun NavGraphBuilder.searchScreen(navController: NavHostController) {
    composable(
        route = Destination.SearchDestination.route
    ) {
        val viewModel: SearchViewModel = hiltViewModel()
        val state = viewModel.stateFlow.collectAsState()
        SearchScreen(
            state = state.value,
            onAction = viewModel::onAction,
            onClickBack = navController::navigateUp,
            navigateToDetail = navController::navigateToDetail
        )
    }
}

fun NavController.navigateToSearch() {
    navigate(Destination.SearchDestination.route) {
        launchSingleTop = true
    }
}