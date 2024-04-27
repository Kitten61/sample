package ru.webant.app.di.error_handler

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webant.domain.error.ErrorHandler
import ru.webant.gateway.error_handler.ErrorHandlerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorHandlerModule {

    @Binds
    @Singleton
    abstract fun bindErrorHandler(
        errorHandler: ErrorHandlerImpl
    ): ErrorHandler
}