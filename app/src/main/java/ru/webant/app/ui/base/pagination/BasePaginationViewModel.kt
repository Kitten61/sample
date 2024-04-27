package ru.webant.app.ui.base.pagination

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.webant.domain.result.Result
import ru.webant.app.ui.base.BaseViewActions
import ru.webant.app.ui.base.BaseViewEvents
import ru.webant.app.ui.base.BaseViewModel
import ru.webant.app.ui.base.recycler_view.BaseScreenModel
import ru.webant.domain.entities.BaseEntity
import ru.webant.domain.error.ErrorHandler
import ru.webant.gateway.utils.toResult
import timber.log.Timber
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KProperty1
import kotlin.reflect.full.findParameterByName
import kotlin.reflect.full.functions


abstract class BasePaginationViewModel<
        ScreenModel : BaseScreenModel,
        ViewState : BasePaginationViewState<ScreenModel>,
        ViewEvents : BaseViewEvents,
        Entity : BaseEntity,
        ViewActions : BaseViewActions>(
    state: ViewState
) : BaseViewModel<ViewState, ViewEvents, ViewActions>(state) {

    abstract val errorHandler: ErrorHandler

    private var page = 1


    fun handlePaginationActions(action: BasePaginationViewActions) {
        when (action) {
            is BasePaginationViewActions.OnSwipeRefresh -> refresh()
            is BasePaginationViewActions.OnLoadNewItems -> loadPage()
        }
    }

    protected abstract fun getItems(page: Int): Single<List<Entity>>

    protected abstract fun screenModelMapper(baseEntities: List<Entity>): List<ScreenModel>

    abstract fun getStateKClass(): KClass<ViewState>

    protected open fun loadPage() {
        withState { state ->
            if (state.isLoading) {
                return@withState
            }
            loadNextPage()
        }
    }

    protected fun loadNextPage() {
        getItems(page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                setStateParam(BasePaginationViewState<ScreenModel>::isLoading, true)
            }
            .toResult(errorHandler)
            .doOnSuccess { page++ }
            .doFinally {
                setStateParam(BasePaginationViewState<ScreenModel>::isLoading, false)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result) {
                    is Result.Success -> {
                        val mappedData = screenModelMapper(result.data)
                        withState {
                            setStateParam(BasePaginationViewState<ScreenModel>::data, it.data + mappedData)
                        }
                    }
                    is Result.Error -> {
                        // TODO
                    }
                }
            }, {
                it.printStackTrace()
            })
            .disposeOnCleared()
    }

    protected fun loadFirstPage(isNeedToShowSwipeRefresh: Boolean = false) {
        getItems(page)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                if (isNeedToShowSwipeRefresh) {
                    setStateParam(BasePaginationViewState<ScreenModel>::isRefreshing, true)
                }
                setStateParam(
                    BasePaginationViewState<ScreenModel>::isNeedToShowPlaceholder,
                    false
                )
                setStateParam(BasePaginationViewState<ScreenModel>::isLoading, true)
            }
            .toResult(errorHandler)
            .doOnSuccess { page++ }
            .doOnError {
                setStateParam(
                    BasePaginationViewState<ScreenModel>::isNeedToShowPlaceholder,
                    true
                )
            }
            .doFinally {
                if (isNeedToShowSwipeRefresh) {
                    setStateParam(BasePaginationViewState<ScreenModel>::isRefreshing, false)
                }
                setStateParam(BasePaginationViewState<ScreenModel>::isLoading, false)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result) {
                    is Result.Success -> {
                        val mappedData = screenModelMapper(result.data)
                        setStateParam(BasePaginationViewState<ScreenModel>::data, mappedData)
                        if (result.data.isEmpty()) {
                            setStateParam(
                                BasePaginationViewState<ScreenModel>::isNeedToShowPlaceholder,
                                true
                            )
                        }
                    }
                    is Result.Error -> {
                        // TODO
                    }
                }
            }, {
                it.printStackTrace()
            })
            .disposeOnCleared()
    }

    protected fun refresh() {
        withState {
            if (!it.isLoading) {
                Timber.i("## PAGINATION | refreshing")
                page = 1
                setStateParam(BasePaginationViewState<ScreenModel>::isLoading, true)
                setStateParam(BasePaginationViewState<ScreenModel>::isRefreshing, true)
                loadFirstPage(isNeedToShowSwipeRefresh = true)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> setStateParam(
        prop: KProperty1<BasePaginationViewState<ScreenModel>, T>,
        state: T?
    ) {
        setState {
            val kClass = getStateKClass()
            val copyFun = kClass::functions.get().find { it.name == "copy" }
                ?: throw IllegalStateException("Ensure that ${this::class.simpleName ?: "ViewState"} is data class")
            val params = copyFun::parameters.get()
            val resultParams = mutableMapOf<KParameter, Any?>()
            val instanceParameter = copyFun.parameters[0]
            resultParams[instanceParameter] = this
            val param = params.find { it.name == prop.name }
                ?: throw IllegalStateException("Field ${prop.name} not found")
            resultParams[param] = state
            copyFun.callBy(resultParams) as? ViewState
                ?: throw IllegalStateException(
                    "Ensure that ${this::class.simpleName ?: "ViewState"} implementing BasePaginationViewState"
                )
        }
    }
}