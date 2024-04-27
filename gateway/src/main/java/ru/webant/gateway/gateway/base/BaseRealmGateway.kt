package ru.webant.gateway.gateway.base

import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import io.realm.RealmObject
import ru.webant.domain.entities.BaseEntity
import ru.webant.gateway.entities.realm.base.BaseRealmMapper


open class BaseRealmGateway(
    private val _realm: Lazy<Realm>,
    val realmScheduler: Scheduler
) {

    protected val realm: Realm
        get() {
            return _realm.get()
        }


    fun <R : RealmObject, E : BaseEntity> withMapper(
        mapper: BaseRealmMapper<R, E>,
        block: () -> Single<R>
    ): Single<E> = block().flatMap {
        Single.just(mapper.map(it))
    }.subscribeOn(realmScheduler)

    fun <R : RealmObject, E : BaseEntity> withMapper(
        entity: E,
        mapper: BaseRealmMapper<R, E>,
        block: (R) -> Unit
    ) = Completable.fromAction {
        val realmModel = mapper.map(entity)
        block(realmModel)
    }.subscribeOn(realmScheduler)
}