package ru.webant.app.ui.favorites

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.webant.app.di.viewmodel.AssistedViewModelFactory
import ru.webant.app.di.viewmodel.hiltMavericksViewModelFactory
import ru.webant.app.ui.base.pagination.BasePaginationViewModel
import ru.webant.app.ui.favorites.recycler_view.FavoriteModel
import ru.webant.domain.entities.FavoriteEntity
import ru.webant.domain.error.ErrorHandler
import ru.webant.domain.gateway.FavoritesGateway
import ru.webant.domain.result.Result
import ru.webant.gateway.utils.toResult
import kotlin.reflect.KClass

class FavoritesViewModel @AssistedInject constructor(
    @Assisted state: FavoritesViewState,
    override val errorHandler: ErrorHandler,
    private val favoritesGateway: FavoritesGateway
) : BasePaginationViewModel<
        FavoriteModel,
        FavoritesViewState,
        FavoritesViewEvents,
        FavoriteEntity,
        FavoritesViewActions
>(state) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<FavoritesViewModel, FavoritesViewState>

    init {
        loadFirstPage(isNeedToShowSwipeRefresh = true)
    }

    override fun getItems(page: Int): Single<List<FavoriteEntity>> {
        return favoritesGateway.fetchImages(page)
    }

    override fun getStateKClass(): KClass<FavoritesViewState> {
        return FavoritesViewState::class
    }

    override fun handle(action: FavoritesViewActions) {
        when (action) {
            is FavoritesViewActions.OnFavoritesClicked -> handleOnfavoritesClicked(action)
        }
    }

    override fun screenModelMapper(baseEntities: List<FavoriteEntity>): List<FavoriteModel> {
        return baseEntities.map {
            FavoriteModel(
                id = it.id,
                url = it.image.url,
                isFavorite = true
            )
        }
    }

    private fun handleOnfavoritesClicked(action: FavoritesViewActions.OnFavoritesClicked) {
        favoritesGateway.deleteFavorite(favoriteId = action.item.id)
            .subscribeOn(Schedulers.io())
            .toResult(errorHandler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result) {
                    is Result.Success -> {
                        setState {
                            copy(data = data.filter {
                                it.id != action.item.id
                            })
                        }
                    }
                    is Result.Error -> {

                    }
                }
            }, {
                it.printStackTrace()
            })
            .disposeOnCleared()
    }

    companion object :
        MavericksViewModelFactory<FavoritesViewModel, FavoritesViewState> by hiltMavericksViewModelFactory()

}