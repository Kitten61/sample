package ru.webant.app.ui.cats

import ru.webant.app.ui.base.BaseViewActions
import ru.webant.app.ui.cats.recycler_view.CatModel
import ru.webant.app.ui.cats.recycler_view.Order

sealed class CatsViewActions : BaseViewActions {
    data class OnFavoritesClicked(
        val item: CatModel
    ) : CatsViewActions()

    data class OnFilterChanged(
        val order: Order
    ) : CatsViewActions()
}