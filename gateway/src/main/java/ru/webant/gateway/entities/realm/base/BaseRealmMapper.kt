package ru.webant.gateway.entities.realm.base

import io.realm.RealmObject
import ru.webant.domain.entities.BaseEntity
import ru.webant.gateway.entities.BaseMapper


interface BaseRealmMapper<R: RealmObject, E: BaseEntity> : BaseMapper<R, E> {

    fun map(realmModel: R): E
    fun map(entity: E): R
}