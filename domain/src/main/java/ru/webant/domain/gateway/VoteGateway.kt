package ru.webant.domain.gateway

import io.reactivex.Completable

interface VoteGateway {

    fun vote(imageId: String, value: Int): Completable
}