package ru.webant.gateway.entities.retrofit.base

import ru.webant.domain.entities.BaseEntity
import ru.webant.gateway.entities.BaseMapper


interface BaseRetrofitMapper<R: BaseRetrofitModel, E: BaseEntity> : BaseMapper<R, E> {

    fun map(retrofitModel: R): E
    fun map(entity: E): R
}