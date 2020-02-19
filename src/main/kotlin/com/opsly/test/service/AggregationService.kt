package com.opsly.test.service

import com.opsly.test.dto.AggregateResult
import reactor.core.publisher.Mono

interface AggregationService {
    fun get(): Mono<AggregateResult>
}