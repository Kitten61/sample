package ru.webant.app.ui.favorites

import ru.webant.app.ui.base.pagination.BasePaginationViewState
import ru.webant.app.ui.favorites.recycler_view.FavoriteModel

data class FavoritesViewState (
    override val data: List<FavoriteModel> = listOf(),
    override val isLoading: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val isNeedToShowPlaceholder: Boolean = false
): BasePaginationViewState<FavoriteModel> {}