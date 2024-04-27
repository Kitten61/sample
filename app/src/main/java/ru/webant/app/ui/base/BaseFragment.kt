package ru.webant.app.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import timber.log.Timber


abstract class BaseFragment<VB: ViewBinding, E: BaseViewEvents> : Fragment(), BaseView<VB> {

    protected abstract val viewModel: BaseViewModel<*, E, *>
    private val uiDisposables = CompositeDisposable()
    private var _views: VB? = null
    protected val views: VB
        get() {
            return _views!!
        }


    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _views = getBinding()
        return views.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupAdapters()
        setupListeners()
        setupUi()
        viewModel.observeViewEvents(::handleEvents)
        registerPopBackStackCallBack()
        super.onViewCreated(view, savedInstanceState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy Activity ${javaClass.simpleName}")
        uiDisposables.dispose()
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _views = null
    }

    override fun setupUi() = Unit

    override fun setupListeners() = Unit

    override fun setupAdapters() = Unit

    override fun invalidate() = Unit

    open fun handleEvents(event: E) = Unit

    protected open fun onBackPressed() {
        findNavController().popBackStack()
    }

    protected fun Disposable.disposeOnDestroy() {
        uiDisposables.add(this)
    }

    protected fun <T : BaseViewEvents> BaseViewModel<*, T, *>.observeViewEvents(observer: (T) -> Unit) {
        viewEvents
            .observe()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                observer(it)
            }
            .disposeOnDestroy()
    }

    private fun registerPopBackStackCallBack() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackPressed()
        }
    }
}