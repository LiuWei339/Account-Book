package com.wl.accountbook.ui.navgraph

sealed class Destination(
    val route: String
) {

    // Main Navigation
    object AbNavigationDestination: Destination("abNavigation")
    object AbNavigatorDestination: Destination("abNavigator")

    // Tabs
    object HomeDestination: Destination("homeScreen")
    object StatsDestination: Destination("statsScreen")
    object RecordEditDestination: Destination("recordEditScreen")

    // home
    object DetailDestination: Destination("detailScreen")
    object SearchDestination: Destination("searchScreen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }

    fun routeWithArgNames(vararg argNames: String): String {
        return buildString {
            append(route)
            argNames.forEach { argName ->
                append("/{$argName}")
            }
        }
    }
}
