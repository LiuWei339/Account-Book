package com.wl.accountbook.ui.search

sealed class SearchAction {
    object Search: SearchAction()
    data class ChangeSearchText(val text: String): SearchAction()
    object START: SearchAction()
}