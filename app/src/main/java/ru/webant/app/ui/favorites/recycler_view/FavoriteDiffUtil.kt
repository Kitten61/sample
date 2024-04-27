package ru.webant.app.ui.favorites.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseDiffUtilCallback

object FavoriteDiffUtil : BaseDiffUtilCallback<FavoriteModel>() {

    override fun areItemsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FavoriteModel, newItem: FavoriteModel): Boolean {
        return oldItem.isFavorite == newItem.isFavorite
    }
}