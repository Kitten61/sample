package ru.webant.app.ui.base.pagination

sealed class BasePaginationViewActions {

    object OnSwipeRefresh : BasePaginationViewActions()
    object OnLoadNewItems : BasePaginationViewActions()
}