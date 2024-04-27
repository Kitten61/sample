package ru.webant.app.ui.uploads

import ru.webant.app.ui.base.pagination.BasePaginationViewState
import ru.webant.app.ui.uploads.recycler_view.UploadsModel

data class UploadsViewState (
    override val data: List<UploadsModel> = listOf(),
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isNeedToShowPlaceholder: Boolean = false
): BasePaginationViewState<UploadsModel>