package ru.webant.app.ui.base

import androidx.viewbinding.ViewBinding
import com.airbnb.mvrx.MavericksView


interface BaseView<VB: ViewBinding> : MavericksView {

    fun setupUi()
    fun setupListeners()
    fun setupAdapters()

    fun getBinding(): VB
}