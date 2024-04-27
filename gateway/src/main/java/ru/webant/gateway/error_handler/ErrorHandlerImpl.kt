package ru.webant.gateway.error_handler

import retrofit2.HttpException
import ru.webant.domain.error.ErrorHandler
import ru.webant.domain.error.BaseError
import ru.webant.gateway.error_handler.errors.NetworkError
import ru.webant.gateway.error_handler.errors.UnknownError
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class ErrorHandlerImpl @Inject constructor() : ErrorHandler {

    override fun handleError(throwable: Throwable): BaseError {
        return when (throwable) {
            is IOException -> NetworkError.BadConnection
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_BAD_GATEWAY,
                    HttpURLConnection.HTTP_INTERNAL_ERROR -> NetworkError.ServerIsUnavailable
                    HttpURLConnection.HTTP_BAD_REQUEST -> NetworkError.BadRequest
                    // Todo: Будет дополняться
                    else -> NetworkError.ServerIsUnavailable
                }
            }
            else -> UnknownError
        }
    }
}