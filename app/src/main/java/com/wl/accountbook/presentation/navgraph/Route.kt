package com.wl.accountbook.presentation.navgraph

sealed class Route(
    route: String
) {

    object HomeScreen: Route("homeScreen")
    object StaticsScreen: Route("staticsScreen")
    object AddRecordScreen: Route("addRecordScreen")

}
