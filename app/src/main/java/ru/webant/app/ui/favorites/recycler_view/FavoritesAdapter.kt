package ru.webant.app.ui.favorites.recycler_view

import android.view.LayoutInflater
import com.bumptech.glide.Glide
import ru.webant.app.databinding.ItemCatBinding
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.base.recycler_view.BaseViewHolder

class FavoritesAdapter(
    private val callback: Callback
) : BaseAdapter<FavoriteModel, ItemCatBinding>(FavoriteDiffUtil) {

    interface Callback {
        fun onFavoritesClicked(item: FavoriteModel)
        fun onItemClicked(item: FavoriteModel)
    }


    override fun createViewHolder(binding: ItemCatBinding): BaseViewHolder<FavoriteModel, ItemCatBinding> {
        return CatViewHolder(binding)
    }

    override fun getBinding(layoutInflater: LayoutInflater): ItemCatBinding {
        return ItemCatBinding.inflate(layoutInflater)
    }

    inner class CatViewHolder(
        views: ItemCatBinding
    ) : BaseViewHolder<FavoriteModel, ItemCatBinding>(views) {

        override fun onBind(item: FavoriteModel) {
            Glide.with(context)
                .load(item.url)
                .into(views.ivCat)
            views.fabFavorite.setOnClickListener {
                callback.onFavoritesClicked(item)
            }
            views.ivCat.setOnClickListener {
                callback.onItemClicked(item)
            }
        }
    }

}