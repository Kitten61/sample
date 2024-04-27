package ru.webant.gateway.gateway.base

import io.reactivex.Completable
import io.reactivex.Single
import ru.webant.domain.entities.BaseEntity
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitMapper
import ru.webant.gateway.entities.retrofit.base.BaseRetrofitModel

open class BaseRetrofitGateway {

    /**
     * Функция, которая маппит модель Retrofit в сущность бизнес-логики
     * @param block Блок кода, в который мы прокидываем полученную модель
     * @return Объект типа Single
     */
    fun <R : BaseRetrofitModel, E : BaseEntity> withMapper(
        mapper: BaseRetrofitMapper<R, E>,
        block: () -> Single<R>
    ): Single<E> {
        return block().flatMap {
            Single.just(mapper.map(it))
        }
    }

    /**
     * Функция, которая маппит сущность в модель для Retrofit
     * @param entity Сущсность, которая будет маппиться
     * @param block Блок кода, в который мы прокидываем полученную модель
     */
    fun <R : BaseRetrofitModel, E : BaseEntity> withMapper(
        mapper: BaseRetrofitMapper<R, E>,
        entity: E,
        block: (R) -> Completable
    ) = Completable.fromAction {
        val retrofitModel = mapper.map(entity)
        block(retrofitModel)
    }
}