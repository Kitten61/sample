package ru.webant.gateway.error_handler

import ru.webant.domain.error.BaseError
import ru.webant.domain.error.ErrorResolver
import ru.webant.gateway.R
import ru.webant.gateway.error_handler.errors.NetworkError
import javax.inject.Inject

class ErrorResolverImpl @Inject constructor(): ErrorResolver {

    override fun getErrorMessage(error: BaseError): Int {
        return when (error) {
            is NetworkError.ServerIsUnavailable,
            is NetworkError.BadRequest,
            is NetworkError.BadConnection -> R.string.server_is_unavailable
            // Todo будет дополняться
            else -> R.string.server_is_unavailable
        }
    }
}