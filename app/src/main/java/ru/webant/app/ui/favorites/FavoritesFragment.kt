package ru.webant.app.ui.favorites

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.mvrx.fragmentViewModel
import ru.webant.app.databinding.FragmentCatsBinding
import ru.webant.app.ui.base.pagination.BasePaginationFragment
import ru.webant.app.ui.favorites.recycler_view.FavoriteModel
import ru.webant.app.ui.favorites.recycler_view.FavoritesAdapter

class FavoritesFragment:  BasePaginationFragment<FavoriteModel, FragmentCatsBinding, FavoritesViewEvents>() {

    override val viewModel: FavoritesViewModel by fragmentViewModel()


    override val recyclerView: RecyclerView by lazy {
        views.rvItems
    }
    override val swipeRefreshLayout: SwipeRefreshLayout by lazy {
        views.swipeRefreshLayout
    }

    override val adapter: FavoritesAdapter = FavoritesAdapter(
        object: FavoritesAdapter.Callback {
            override fun onFavoritesClicked(item: FavoriteModel) {
                viewModel.handle(FavoritesViewActions.OnFavoritesClicked(item))
            }

            override fun onItemClicked(item: FavoriteModel) {
                //TODO("Not yet implemented")
            }

        }
    )

    override fun setupAdapters() {
        views.rvItems.adapter = adapter
        views.rvItems.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
    }

    override fun getBinding(): FragmentCatsBinding = FragmentCatsBinding.inflate(layoutInflater)
}