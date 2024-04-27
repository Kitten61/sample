package ru.webant.app.ui.cats.recycler_view

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import ru.webant.app.R
import ru.webant.app.databinding.ItemCatBinding
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.base.recycler_view.BaseDiffUtilCallback
import ru.webant.app.ui.base.recycler_view.BaseViewHolder

class CatsAdapter(
    private val callback: Callback
) : BaseAdapter<CatModel, ItemCatBinding>(CatsDiffUtil) {

    interface Callback {
        fun onFavoritesClicked(item: CatModel)
        fun onItemClicked(item: CatModel)
    }


    override fun createViewHolder(binding: ItemCatBinding): BaseViewHolder<CatModel, ItemCatBinding> {
        return CatViewHolder(binding)
    }

    override fun getBinding(layoutInflater: LayoutInflater): ItemCatBinding {
        return ItemCatBinding.inflate(layoutInflater)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<CatModel, ItemCatBinding>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val viewHolder = holder as CatViewHolder
            payloads.forEach {
                when (it) {
                    is CatsPayloads.LikeChanged -> viewHolder.updateLike(currentList[position])
                }
            }
        }
    }

    inner class CatViewHolder(
        views: ItemCatBinding
    ) : BaseViewHolder<CatModel, ItemCatBinding>(views) {

        override fun onBind(item: CatModel) {
            Glide.with(context)
                .load(item.url)
                .into(views.ivCat)
            views.fabFavorite.isEnabled = !item.isFavorite
            views.fabFavorite.setOnClickListener {
                callback.onFavoritesClicked(item)
            }
            views.ivCat.setOnClickListener {
                callback.onItemClicked(item)
            }
        }

        fun updateLike(catModel: CatModel) {
            views.fabFavorite.isEnabled = !catModel.isFavorite
        }
    }

}

sealed class CatsPayloads {

    object LikeChanged: CatsPayloads()
}