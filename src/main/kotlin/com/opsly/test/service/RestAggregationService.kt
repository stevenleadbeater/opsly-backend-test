package com.opsly.test.service

import com.opsly.test.dto.AggregateResult
import com.opsly.test.mapper.AggregateResultAccumulator
import com.opsly.test.repository.FacebookRepository
import com.opsly.test.repository.InstagramRepository
import com.opsly.test.repository.TwitterRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class RestAggregationService(
        private val facebookRepository: FacebookRepository,
        private val instagramRepository: InstagramRepository,
        private val twitterRepository: TwitterRepository,
        private val aggregateResultAccumulator: AggregateResultAccumulator) : AggregationService {


    override fun get(): Mono<AggregateResult> {
        return Flux.merge(twitterRepository.get(), facebookRepository.get(), instagramRepository.get())
                .reduce(AggregateResult(ArrayList(), ArrayList(), ArrayList()), aggregateResultAccumulator::accumulate)
    }
}