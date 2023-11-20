package com.wl.accountbook.ui.details

sealed class DetailAction {
    object Delete: DetailAction()
    object Edit: DetailAction()
}