package com.wl.accountbook.ui.navgraph

sealed class Destination(
    route: String
) {

    object HomeDestination: Destination("homeScreen")
    object StaticsDestination: Destination("staticsScreen")
    object AddRecordDestination: Destination("addRecordScreen")

}
