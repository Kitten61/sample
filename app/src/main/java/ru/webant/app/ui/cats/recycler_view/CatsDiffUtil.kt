package ru.webant.app.ui.cats.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseDiffUtilCallback

object CatsDiffUtil : BaseDiffUtilCallback<CatModel>() {

    override fun areItemsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatModel, newItem: CatModel): Boolean {
        return oldItem.isFavorite == newItem.isFavorite
    }

    override fun getChangePayload(oldItem: CatModel, newItem: CatModel): Any? {

        if (oldItem.isFavorite != newItem.isFavorite) {
            return CatsPayloads.LikeChanged
        }

        return super.getChangePayload(oldItem, newItem)
    }
}