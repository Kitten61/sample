package ru.webant.app.ui.base.recycler_view

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffUtilCallback<ScreenModel: BaseScreenModel>: DiffUtil.ItemCallback<ScreenModel>()