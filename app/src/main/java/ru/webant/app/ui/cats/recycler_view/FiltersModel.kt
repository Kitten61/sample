package ru.webant.app.ui.cats.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseScreenModel

data class FiltersModel(
    val type: Order = Order.RANDOM
): BaseScreenModel