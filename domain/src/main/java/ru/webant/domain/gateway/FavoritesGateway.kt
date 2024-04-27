package ru.webant.domain.gateway

import io.reactivex.Completable
import io.reactivex.Single
import ru.webant.domain.entities.FavoriteEntity

interface FavoritesGateway {

    fun createFavorite(imageId: String): Completable
    fun deleteFavorite(favoriteId: String): Completable
    fun fetchImages(page: Int): Single<List<FavoriteEntity>>
}