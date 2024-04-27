package ru.webant.app.di.gateway

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.webant.app.di.app.AppModule
import ru.webant.app.di.realm.RealmModule
import ru.webant.domain.gateway.FavoritesGateway
import ru.webant.domain.gateway.ImagesGateway
import ru.webant.domain.gateway.VoteGateway
import ru.webant.gateway.gateway.RetrofitFavoritesGateway
import ru.webant.gateway.gateway.RetrofitImagesGateway
import ru.webant.gateway.gateway.RetrofitVoteGateway
import javax.inject.Singleton

@Module(includes = [RealmModule::class, AppModule::class])
@InstallIn(SingletonComponent::class)
abstract class GatewayModule {

    @Binds
    @Singleton
    abstract fun bindVoteGateway(gateway: RetrofitVoteGateway): VoteGateway

    @Binds
    @Singleton
    abstract fun bindFavoritesGateway(gateway: RetrofitFavoritesGateway): FavoritesGateway

    @Binds
    @Singleton
    abstract fun bindImagesGateway(gateway: RetrofitImagesGateway): ImagesGateway

}