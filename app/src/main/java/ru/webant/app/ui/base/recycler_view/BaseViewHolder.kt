package ru.webant.app.ui.base.recycler_view

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<ScreenModel : BaseScreenModel, VB: ViewBinding>(
    protected open val views: VB
) : RecyclerView.ViewHolder(views.root) {

    protected val context = views.root.context

    abstract fun onBind(item: ScreenModel)
}