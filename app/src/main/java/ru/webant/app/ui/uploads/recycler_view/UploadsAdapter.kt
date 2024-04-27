package ru.webant.app.ui.uploads.recycler_view

import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import ru.webant.app.databinding.ItemCatBinding
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.base.recycler_view.BaseViewHolder

class UploadsAdapter(
    private val callback: Callback
) : BaseAdapter<UploadsModel, ItemCatBinding>(UploadsDiffUtil) {

    interface Callback {
        fun onItemLongClickListener(item: UploadsModel)
        fun onItemClicked(item: UploadsModel)
    }


    override fun createViewHolder(binding: ItemCatBinding): BaseViewHolder<UploadsModel, ItemCatBinding> {
        return CatViewHolder(binding)
    }

    override fun getBinding(layoutInflater: LayoutInflater): ItemCatBinding {
        return ItemCatBinding.inflate(layoutInflater)
    }

    inner class CatViewHolder(
        views: ItemCatBinding
    ) : BaseViewHolder<UploadsModel, ItemCatBinding>(views) {

        override fun onBind(item: UploadsModel) {
            Glide.with(context)
                .load(item.url)
                .into(views.ivCat)
            views.ivCat.setOnLongClickListener {
                callback.onItemLongClickListener(item)
                true
            }
            views.fabFavorite.isVisible = false
            views.ivCat.setOnClickListener {
                callback.onItemClicked(item)
            }
        }
    }

}