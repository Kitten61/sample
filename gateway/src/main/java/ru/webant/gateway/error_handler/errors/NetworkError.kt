package ru.webant.gateway.error_handler.errors

import ru.webant.domain.error.BaseError

sealed class NetworkError : BaseError {

    object BadConnection : NetworkError()
    object ServerIsUnavailable : NetworkError()
    object BadRequest : NetworkError()
}