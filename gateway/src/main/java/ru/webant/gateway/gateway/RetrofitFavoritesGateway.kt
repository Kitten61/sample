package ru.webant.gateway.gateway

import io.reactivex.Single
import ru.webant.domain.entities.FavoriteEntity
import ru.webant.domain.gateway.FavoritesGateway
import ru.webant.gateway.entities.retrofit.mappers.RetrofitFavoriteMapper
import ru.webant.gateway.entities.retrofit.requests.FavoriteRequest
import ru.webant.gateway.gateway.base.BaseRetrofitGateway
import javax.inject.Inject

class RetrofitFavoritesGateway @Inject constructor(
    private val api: CatsApi
) : FavoritesGateway, BaseRetrofitGateway() {

    override fun createFavorite(imageId: String) =
        api.createFavorite(
            FavoriteRequest(imageId = imageId)
        )

    override fun deleteFavorite(favoriteId: String) =
        api.deleteFavorite(favoriteId = favoriteId)

    override fun fetchImages(page: Int): Single<List<FavoriteEntity>> =
        api.fetchFavorites(page = page)
            .flatMap {
                Single.just(
                    it.map { RetrofitFavoriteMapper.map(it) }
                )
            }
}