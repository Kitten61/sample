package ru.webant.gateway.entities.retrofit.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitModel

@JsonClass(generateAdapter = true)
data class FavoriteResponse(
    val id: String,
    val image: ImageResponse,
    @Json(name = "image_id")
    val imageId: String
): BaseRetrofitModel