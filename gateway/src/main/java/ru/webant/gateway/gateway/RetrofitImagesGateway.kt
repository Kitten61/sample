package ru.webant.gateway.gateway

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import ru.webant.domain.entities.ImageEntity
import ru.webant.domain.gateway.ImagesGateway
import ru.webant.gateway.entities.retrofit.mappers.RetrofitImageMapper
import ru.webant.gateway.gateway.base.BaseRetrofitGateway
import java.io.File
import javax.inject.Inject

class RetrofitImagesGateway @Inject constructor(
    private val api: CatsApi
): ImagesGateway, BaseRetrofitGateway()  {

    override fun getPublicImages(page: Int, order: String): Single<List<ImageEntity>> =
        api.fetchPublicImages(page = page, order = order)
            .flatMap {
                Single.just(
                       it.map { RetrofitImageMapper.map(it) }
                )
            }

    override fun getUserImages(page: Int): Single<List<ImageEntity>> =
        api.fetchUserImages(page = page)
            .flatMap {
                Single.just(
                    it.map { RetrofitImageMapper.map(it) }
                )
            }

    override fun uploadFile(part: MultipartBody.Part) = api.uploadFile(part)

    override fun deleteFile(imageId: String) = api.deleteImage(imageId)
}