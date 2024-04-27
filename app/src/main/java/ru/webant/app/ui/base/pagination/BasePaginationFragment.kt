package ru.webant.app.ui.base.pagination

import android.view.View
import androidx.annotation.CallSuper
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import ru.webant.app.ui.base.BaseFragment
import ru.webant.app.ui.base.BaseViewEvents
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.base.recycler_view.BaseScreenModel
import ru.webant.app.ui.base.recycler_view.PaginationConstants.PAGE_LIMIT
import ru.webant.app.ui.base.recycler_view.PaginationScrollListener

abstract class BasePaginationFragment<
        ScreenModel : BaseScreenModel,
        VB : ViewBinding,
        E : BaseViewEvents
        > : BaseFragment<VB, E>() {

    abstract override val viewModel: BasePaginationViewModel<ScreenModel, *, E, *, *>

    abstract val recyclerView: RecyclerView
    abstract val swipeRefreshLayout: SwipeRefreshLayout
    open val placeholder: View? = null
    abstract val adapter: BaseAdapter<ScreenModel, *>


    @CallSuper
    override fun setupListeners() {
        viewModel.onEach {
            adapter.submitList(it.data)
            swipeRefreshLayout.isRefreshing = it.isRefreshing
            placeholder?.isVisible = it.isNeedToShowPlaceholder
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.handlePaginationActions(BasePaginationViewActions.OnSwipeRefresh)
        }
        recyclerView.addOnScrollListener(PaginationScrollListener(PAGE_LIMIT) {
            viewModel.handlePaginationActions(BasePaginationViewActions.OnLoadNewItems)
        })
    }
}
