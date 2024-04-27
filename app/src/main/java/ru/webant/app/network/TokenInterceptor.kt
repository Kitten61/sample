package ru.webant.app.network

import ru.webant.app.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection


object TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return try {
            chain.proceed(
                request
                    .newBuilder()
                    .addHeader("x-api-key", BuildConfig.API_KEY)
                    .build()
            )
        } catch (e: Exception) {
            val message = e.message
            e.printStackTrace()
            Response.Builder()
                .body((message ?: "").toResponseBody("-".toMediaTypeOrNull()))
                .protocol(Protocol.HTTP_2)
                .code(HttpURLConnection.HTTP_NOT_FOUND)
                .request(request)
                .message(message ?: "")
                .build()
        }
    }
}
