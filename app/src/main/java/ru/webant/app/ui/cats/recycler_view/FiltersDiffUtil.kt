package ru.webant.app.ui.cats.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseDiffUtilCallback

object FiltersDiffUtil : BaseDiffUtilCallback<FiltersModel>() {

    override fun areItemsTheSame(oldItem: FiltersModel, newItem: FiltersModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FiltersModel, newItem: FiltersModel): Boolean {
        return oldItem.type == newItem.type
    }
}