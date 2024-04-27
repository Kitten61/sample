package ru.webant.app.ui.base

import android.os.Bundle
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber


abstract class BaseActivity<VB: ViewBinding>: AppCompatActivity(), BaseView<VB> {

    protected abstract val viewModel: BaseViewModel<*, *, *>
    private val uiDisposables = CompositeDisposable()
    private var _views: VB? = null
    protected val views: VB
        get() = _views!!

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _views = getBinding()
        setContentView(views.root)
        setupListeners()
        registerPopBackStackCallBack()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Activity ${javaClass.simpleName}")
        uiDisposables.dispose()
        _views = null
    }

    override fun invalidate() = Unit
    override fun setupUi() = Unit
    override fun setupListeners() = Unit
    override fun setupAdapters() = Unit

    protected fun <T : BaseViewEvents> BaseViewModel<*, T, *>.observeViewEvents(observer: (T) -> Unit) {
        viewEvents
            .observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                observer(it)
            }
            .disposeOnDestroy()
    }

    protected fun Disposable.disposeOnDestroy() {
        uiDisposables.add(this)
    }

    private fun registerPopBackStackCallBack() {
        onBackPressedDispatcher.addCallback(this) {
            finish()
        }
    }
}