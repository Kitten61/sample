package ru.webant.app.ui.base.pagination

import ru.webant.app.ui.base.BaseViewState
import ru.webant.app.ui.base.recycler_view.BaseScreenModel

interface BasePaginationViewState<T : BaseScreenModel> : BaseViewState {

    val data: List<T>
    val isLoading: Boolean
    val isRefreshing: Boolean
    val isNeedToShowPlaceholder: Boolean
}