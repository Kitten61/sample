package ru.webant.app.ui.base

import com.airbnb.mvrx.MavericksViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import ru.webant.app.di.viewmodel.DataSource
import ru.webant.app.di.viewmodel.PublishDataSource

abstract class BaseViewModel<S: BaseViewState, VE: BaseViewEvents, VA: BaseViewActions>(
    state: S
) : MavericksViewModel<S>(state) {

    private val disposable = CompositeDisposable()
    protected val _viewEvents = PublishDataSource<VE>()
    val viewEvents: DataSource<VE> = _viewEvents


    open fun handle(action: VA) = Unit

    fun Disposable.disposeOnCleared() {
        disposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}