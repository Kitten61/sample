package ru.webant.app.ui.cats.recycler_view

import android.view.LayoutInflater
import ru.webant.app.databinding.ItemFiltersBinding
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.base.recycler_view.BaseViewHolder

class FiltersAdapter(
    private val callback: Callback
) : BaseAdapter<FiltersModel, ItemFiltersBinding>(FiltersDiffUtil) {

    interface Callback {
        fun onItemChecked(order: Order)
    }


    override fun createViewHolder(binding: ItemFiltersBinding): BaseViewHolder<FiltersModel, ItemFiltersBinding> {
        return FiltersViewHolder(binding)
    }

    override fun getBinding(layoutInflater: LayoutInflater): ItemFiltersBinding {
        return ItemFiltersBinding.inflate(layoutInflater)
    }

    inner class FiltersViewHolder(
        views: ItemFiltersBinding
    ) : BaseViewHolder<FiltersModel, ItemFiltersBinding>(views) {

        override fun onBind(item: FiltersModel) {
            views.rgFilters.setOnCheckedChangeListener { _, i ->
                val order = when (i) {
                    0 -> Order.RANDOM
                    1 -> Order.ASC
                    else -> Order.DESC
                }
                callback.onItemChecked(order)
            }
            val selected = when (item.type) {
                Order.RANDOM -> 0
                Order.ASC -> 1
                Order.DESC -> 2
            }
            views.rgFilters.check(selected)
        }
    }

}