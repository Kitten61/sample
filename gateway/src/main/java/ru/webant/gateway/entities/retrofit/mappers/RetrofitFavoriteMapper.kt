package ru.webant.gateway.entities.retrofit.mappers

import ru.webant.domain.entities.FavoriteEntity
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitMapper
import ru.webant.gateway.entities.retrofit.response.FavoriteResponse

object RetrofitFavoriteMapper: BaseRetrofitMapper<FavoriteResponse, FavoriteEntity> {

    override fun map(retrofitModel: FavoriteResponse): FavoriteEntity {
        return FavoriteEntity(
            id = retrofitModel.id,
            imageId = retrofitModel.imageId,
            image = RetrofitImageMapper.map(retrofitModel.image)
        )
    }

    override fun map(entity: FavoriteEntity): FavoriteResponse {
        TODO("Not yet implemented")
    }
}