package ru.webant.app.ui.uploads.recycler_view

import ru.webant.app.ui.base.recycler_view.BaseDiffUtilCallback

object UploadsDiffUtil : BaseDiffUtilCallback<UploadsModel>() {

    override fun areItemsTheSame(oldItem: UploadsModel, newItem: UploadsModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UploadsModel, newItem: UploadsModel): Boolean {
        return oldItem.id == newItem.id
    }
}