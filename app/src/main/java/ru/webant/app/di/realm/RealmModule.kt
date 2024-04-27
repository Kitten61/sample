package ru.webant.app.di.realm

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RealmModule {

    @Provides
    @Singleton
    fun provideRealm(realmConfiguration: RealmConfiguration) : Realm {
        Realm.setDefaultConfiguration(realmConfiguration)
        return Realm.getDefaultInstance()
    }

    @Provides
    @Singleton
    fun provideRealmConfiguration(): RealmConfiguration {
        return RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
    }

    @Provides
    @Singleton
    fun provideRealmScheduler(): Scheduler {
        return Schedulers.from(Executors.newSingleThreadExecutor())
    }
}