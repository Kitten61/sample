package ru.webant.app.di.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import ru.webant.app.di.retrofit.RetrofitModule
import ru.webant.gateway.gateway.CatsApi
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(
        retrofit: Retrofit
    ): CatsApi {
        return retrofit.create(CatsApi::class.java)
    }
}