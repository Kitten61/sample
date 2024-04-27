package ru.webant.domain.error

interface ErrorResolver {

    fun getErrorMessage(error: BaseError): Int
}