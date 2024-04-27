package ru.webant.app.ui.base.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<ScreenModel : BaseScreenModel, VB: ViewBinding>(
    diffUtilCallback: BaseDiffUtilCallback<ScreenModel>
) : ListAdapter<ScreenModel, BaseViewHolder<ScreenModel, VB>>(diffUtilCallback) {


    abstract fun createViewHolder(binding: VB): BaseViewHolder<ScreenModel, VB>

    abstract fun getBinding(layoutInflater: LayoutInflater): VB

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ScreenModel, VB> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val views = getBinding(layoutInflater)
        return createViewHolder(views)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ScreenModel, VB>, position: Int) {
        holder.onBind(currentList[position])
    }
}