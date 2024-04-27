package ru.webant.domain.error

interface ErrorHandler {

    fun handleError(throwable: Throwable): BaseError
}