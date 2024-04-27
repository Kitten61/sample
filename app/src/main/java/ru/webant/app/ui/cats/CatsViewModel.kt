package ru.webant.app.ui.cats

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
import ru.webant.app.ui.cats.recycler_view.CatModel
import ru.webant.app.ui.cats.recycler_view.Order
import ru.webant.domain.entities.ImageEntity
import ru.webant.domain.error.ErrorHandler
import ru.webant.domain.gateway.FavoritesGateway
import ru.webant.domain.gateway.ImagesGateway
import ru.webant.domain.result.Result
import ru.webant.gateway.utils.toResult
import kotlin.reflect.KClass

class CatsViewModel @AssistedInject constructor(
    @Assisted state: CatsViewState,
    override val errorHandler: ErrorHandler,
    private val imagesGateway: ImagesGateway,
    private val favoritesGateway: FavoritesGateway
) : BasePaginationViewModel<
        CatModel,
        CatsViewState,
        CatsViewEvents,
        ImageEntity,
        CatsViewActions
        >(state) {

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<CatsViewModel, CatsViewState>

    init {
        loadFirstPage(isNeedToShowSwipeRefresh = true)
    }

    override fun getItems(page: Int): Single<List<ImageEntity>> {
        var order: Order = Order.RANDOM
        withState {
            order = it.filtersModel.type
        }
        return imagesGateway.getPublicImages(page, order.toString())
    }

    override fun getStateKClass(): KClass<CatsViewState> {
        return CatsViewState::class
    }

    override fun handle(action: CatsViewActions) {
        when (action) {
            is CatsViewActions.OnFavoritesClicked -> handleOnfavoritesClicked(action)
            is CatsViewActions.OnFilterChanged -> handleOnFilterChanged(action)
        }
    }

    private fun handleOnFilterChanged(action: CatsViewActions.OnFilterChanged) {
        refresh()
        setState {
            copy(
                filtersModel = filtersModel.copy(type = action.order)
            )
        }
    }

    override fun screenModelMapper(baseEntities: List<ImageEntity>): List<CatModel> {
        return baseEntities.map {
            CatModel(
                id = it.id,
                url = it.url,
                isFavorite = false
            )
        }
    }

    private fun handleOnfavoritesClicked(action: CatsViewActions.OnFavoritesClicked) {
        favoritesGateway.createFavorite(imageId = action.item.id)
            .subscribeOn(Schedulers.io())
            .toResult(errorHandler)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                when (result) {
                    is Result.Success -> {
                        setState {
                            copy(data = data.map {
                                if (it.id == action.item.id) {
                                    it.copy(isFavorite = true)
                                } else {
                                    it
                                }
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
        MavericksViewModelFactory<CatsViewModel, CatsViewState> by hiltMavericksViewModelFactory()

}