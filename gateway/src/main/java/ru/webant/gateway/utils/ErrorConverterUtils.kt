package ru.webant.gateway.utils

import io.reactivex.Completable
import io.reactivex.Single
import ru.webant.domain.error.ErrorHandler
import ru.webant.domain.result.Result

fun <T> Single<T>.toResult(errorHandler: ErrorHandler): Single<Result<T>> = this
    .map<Result<T>> { Result.Success(it) }
    .onErrorReturn {
        Result.Error(errorHandler.handleError(it))
    }

fun Completable.toResult(errorHandler: ErrorHandler): Single<Result<Boolean>> =
    this.toSingleDefault(true)
        .map<Result<Boolean>> { Result.Success(it) }
        .onErrorReturn {
            Result.Error(errorHandler.handleError(it))
        }