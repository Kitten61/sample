package ru.webant.app.ui.cats

import ru.webant.app.ui.base.pagination.BasePaginationViewState
import ru.webant.app.ui.cats.recycler_view.CatModel
import ru.webant.app.ui.cats.recycler_view.FiltersModel

data class CatsViewState (
    override val data: List<CatModel> = listOf(),
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isNeedToShowPlaceholder: Boolean = false,
    val filtersModel: FiltersModel = FiltersModel()
): BasePaginationViewState<CatModel> {}