package ru.webant.app.ui.favorites

import ru.webant.app.ui.base.BaseViewActions
import ru.webant.app.ui.favorites.recycler_view.FavoriteModel

sealed class FavoritesViewActions : BaseViewActions {
    data class OnFavoritesClicked(
        val item: FavoriteModel
    ) : FavoritesViewActions()
}