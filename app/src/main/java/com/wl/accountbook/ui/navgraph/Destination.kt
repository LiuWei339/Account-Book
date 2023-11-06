package com.wl.accountbook.ui.navgraph

sealed class Destination(
    val route: String
) {

    // Tabs
    object HomeDestination: Destination("homeScreen")
    object StaticsDestination: Destination("staticsScreen")
    object AddRecordDestination: Destination("addRecordScreen")

    // Main Navigation
    object AbNavigationDestination: Destination("abNavigation")
    object AbNavigatorDestination: Destination("abNavigator")


}
