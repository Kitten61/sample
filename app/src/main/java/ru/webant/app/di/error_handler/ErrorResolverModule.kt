package ru.webant.aquanayada.di.error_handler

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webant.domain.error.ErrorResolver
import ru.webant.gateway.error_handler.ErrorResolverImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorResolverModule {

    @Binds
    @Singleton
    abstract fun bindErrorResolver(
        errorMessengerImpl: ErrorResolverImpl
    ): ErrorResolver
}