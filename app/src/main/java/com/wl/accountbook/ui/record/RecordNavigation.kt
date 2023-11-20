package com.wl.accountbook.ui.record

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wl.accountbook.ui.navgraph.Destination

internal const val recordCreateTimeArg = "recordCreateTime"

internal class RecordArgs(val recordCreateTime: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(savedStateHandle[recordCreateTimeArg] ?: -1L)
}

fun NavGraphBuilder.recordEditScreen(navController: NavHostController) {
    composable(
        route = Destination.RecordEditDestination.routeWithArgNames(recordCreateTimeArg),
        arguments = listOf(navArgument(recordCreateTimeArg) { type = NavType.LongType })
    ) {
        val viewModel: RecordViewModel = hiltViewModel()
        RecordEditScreen(
            state = viewModel.recordState,
            calcState = viewModel.calcState,
            onAction = viewModel::onRecordAction,
            navigateUp = navController::navigateUp
        )
    }
}

fun NavController.navigateToRecord(recordCreateTime: Long = -1L) {
    navigate(Destination.RecordEditDestination.withArgs(recordCreateTime.toString())) {
        launchSingleTop = true
    }
}
