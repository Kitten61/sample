package ru.webant.gateway.entities.retrofit.requests

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitModel

@JsonClass(generateAdapter = true)
data class FavoriteRequest(
    @Json(name = "image_id")
    val imageId: String
): BaseRetrofitModel