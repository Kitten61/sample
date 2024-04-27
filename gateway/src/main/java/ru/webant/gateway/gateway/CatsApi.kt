package ru.webant.gateway.gateway

import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*
import ru.webant.gateway.entities.retrofit.requests.FavoriteRequest
import ru.webant.gateway.entities.retrofit.requests.VoteRequest
import ru.webant.gateway.entities.retrofit.response.FavoriteResponse
import ru.webant.gateway.entities.retrofit.response.ImageResponse
import ru.webant.gateway.entities.retrofit.response.PaginationResponse
import java.util.*

interface CatsApi {

    @GET("/v1/images/search")
    fun fetchPublicImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10,
        @Query("order") order: String
    ): Single<PaginationResponse<ImageResponse>>

    @GET("/v1/images/")
    fun fetchUserImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Single<PaginationResponse<ImageResponse>>

    @GET("/v1/favourites")
    fun fetchFavorites(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 10
    ): Single<PaginationResponse<FavoriteResponse>>

    @DELETE("/v1/favourites/{favorite_id}")
    fun deleteFavorite(
        @Path("favorite_id") favoriteId: String
    ): Completable

    @DELETE("/v1//images/{image_id}")
    fun deleteImage(
        @Path("image_id") imageId: String
    ): Completable

    @POST("/v1/favourites/")
    fun createFavorite(
        @Body body: FavoriteRequest
    ): Completable

    @Multipart
    @POST("/v1/images/upload")
    fun uploadFile(
        @Part file: MultipartBody.Part
    ): Completable

    @POST("/v1/votes")
    fun vote(
        @Body body: VoteRequest
    ): Completable
}