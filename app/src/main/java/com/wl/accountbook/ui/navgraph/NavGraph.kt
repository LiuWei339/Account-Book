package com.wl.accountbook.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.wl.accountbook.ui.navbar.AbNavigator

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Destination.AbNavigationDestination.route,
            startDestination = Destination.AbNavigatorDestination.route
        ) {
            composable(route = Destination.AbNavigatorDestination.route) {
                AbNavigator()
            }
        }
    }
}