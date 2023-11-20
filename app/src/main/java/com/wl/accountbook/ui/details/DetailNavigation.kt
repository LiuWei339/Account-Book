package com.wl.accountbook.ui.details

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.wl.accountbook.ui.navgraph.Destination
import com.wl.accountbook.ui.record.navigateToRecord


internal const val recordCreateTimeArg = "recordCreateTime"

internal class DetailArgs(val recordCreateTime: Long) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[recordCreateTimeArg]) as Long)
}

fun NavGraphBuilder.detailScreen(navController: NavHostController) {
    composable(
        route = Destination.DetailDestination.routeWithArgNames(recordCreateTimeArg),
        arguments = listOf(navArgument(recordCreateTimeArg) { type = NavType.LongType })
    ) {
        val viewModel: DetailViewModel = hiltViewModel()
        viewModel.state?.let {
            DetailScreen(
                moneyRecordAndType = it,
                onClickDelete = viewModel::delete,
                onClickEdit = { navController.navigateToRecord(it.createTime) },
                navigateUp = navController::navigateUp
            )
        }
    }
}

fun NavController.navigateToDetail(recordCreateTime: Long) {
    navigate(Destination.DetailDestination.withArgs(recordCreateTime.toString())) {
        launchSingleTop = true
    }
}
