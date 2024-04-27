package ru.webant.domain.result

import ru.webant.domain.error.BaseError

sealed class Result<T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: BaseError) : Result<T>()
}