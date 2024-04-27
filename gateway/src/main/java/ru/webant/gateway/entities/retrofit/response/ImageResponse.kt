package ru.webant.gateway.entities.retrofit.response

import com.squareup.moshi.JsonClass
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitModel

@JsonClass(generateAdapter = true)
data class ImageResponse(
    val id: String,
    val url: String
): BaseRetrofitModel