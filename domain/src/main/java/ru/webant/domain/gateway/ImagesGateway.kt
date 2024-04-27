package ru.webant.domain.gateway

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import ru.webant.domain.entities.ImageEntity
import java.io.File

interface ImagesGateway {

    fun getPublicImages(page: Int, order: String): Single<List<ImageEntity>>
    fun getUserImages(page: Int): Single<List<ImageEntity>>
    fun uploadFile(part: MultipartBody.Part): Completable
    fun deleteFile(fileId: String): Completable
}