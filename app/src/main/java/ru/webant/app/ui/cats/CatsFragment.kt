package ru.webant.app.ui.cats

import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.fragmentViewModel
import ru.webant.app.ui.base.BaseFragment
import ru.webant.app.databinding.FragmentCatsBinding
import ru.webant.app.ui.base.pagination.BasePaginationFragment
import ru.webant.app.ui.base.recycler_view.BaseAdapter
import ru.webant.app.ui.cats.recycler_view.CatModel
import ru.webant.app.ui.cats.recycler_view.CatsAdapter
import ru.webant.app.ui.cats.recycler_view.FiltersAdapter
import ru.webant.app.ui.cats.recycler_view.Order

class CatsFragment:  BasePaginationFragment<CatModel, FragmentCatsBinding, CatsViewEvents>() {

    override val viewModel: CatsViewModel by fragmentViewModel()


    override val recyclerView: RecyclerView by lazy {
        views.rvItems
    }
    override val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        views.swipeRefreshLayout
    }

    override val adapter: CatsAdapter = CatsAdapter(
        object: CatsAdapter.Callback {
            override fun onFavoritesClicked(item: CatModel) {
                viewModel.handle(CatsViewActions.OnFavoritesClicked(item))
            }

            override fun onItemClicked(item: CatModel) {
                //TODO("Not yet implemented")
            }

        }
    )

    private val filtersAdapter = FiltersAdapter(
        object : FiltersAdapter.Callback {

            override fun onItemChecked(order: Order) {
                viewModel.handle(CatsViewActions.OnFilterChanged(order))
            }
        }
    )
    private val concatAdapter = ConcatAdapter(filtersAdapter, adapter)


    override fun setupListeners() {
        super.setupListeners()
        viewModel.onEach(CatsViewState::filtersModel) {
            filtersAdapter.submitList(listOf(it))
        }
    }

    override fun setupAdapters() {
        views.rvItems.adapter = concatAdapter
        views.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

                override fun getSpanSize(position: Int): Int {
                    return if (position == 0) {
                        2
                    } else {
                        1
                    }
                }
            }
        }
    }

    override fun getBinding(): FragmentCatsBinding = FragmentCatsBinding.inflate(layoutInflater)
}