package ru.webant.app.ui.main

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.webant.app.di.viewmodel.AssistedViewModelFactory
import ru.webant.app.di.viewmodel.hiltMavericksViewModelFactory
import ru.webant.app.ui.base.BaseViewModel


class MainViewModel @AssistedInject constructor(
    @Assisted state: MainActivityViewState
) : BaseViewModel<MainActivityViewState, MainActivityViewEvents, MainActivityViewActions>(state) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<MainViewModel, MainActivityViewState>


    companion object : MavericksViewModelFactory<MainViewModel, MainActivityViewState>
        by hiltMavericksViewModelFactory()
}