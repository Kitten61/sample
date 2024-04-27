package ru.webant.app.ui.favorites.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseScreenModel

data class FavoriteModel(
    val id: String,
    val url: String,
    val isFavorite: Boolean
): BaseScreenModel