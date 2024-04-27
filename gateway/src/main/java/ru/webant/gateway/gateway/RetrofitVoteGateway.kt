package ru.webant.gateway.gateway

import ru.webant.domain.gateway.VoteGateway
import ru.webant.gateway.entities.retrofit.requests.VoteRequest
import ru.webant.gateway.gateway.base.BaseRetrofitGateway
import javax.inject.Inject


class RetrofitVoteGateway @Inject constructor(
    private val api: CatsApi
) : VoteGateway, BaseRetrofitGateway() {

    override fun vote(imageId: String, value: Int) =
        api.vote(
            VoteRequest(
                imageId = imageId,
                value = value
            )
        )
}