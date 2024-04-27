package ru.webant.gateway.entities.retrofit.mappers

import ru.webant.domain.entities.ImageEntity
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitMapper
import ru.webant.gateway.entities.retrofit.response.ImageResponse

object RetrofitImageMapper: BaseRetrofitMapper<ImageResponse, ImageEntity> {

    override fun map(retrofitModel: ImageResponse): ImageEntity {
        return ImageEntity(
            id = retrofitModel.id,
            url = retrofitModel.url
        )
    }

    override fun map(entity: ImageEntity): ImageResponse {
        TODO("Not yet implemented")
    }
}