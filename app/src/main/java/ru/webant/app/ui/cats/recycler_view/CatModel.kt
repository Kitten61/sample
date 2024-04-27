package ru.webant.app.ui.cats.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseScreenModel

data class CatModel(
    val id: String,
    val url: String,
    val isFavorite: Boolean
): BaseScreenModel