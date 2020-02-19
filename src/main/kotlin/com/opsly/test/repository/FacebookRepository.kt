package com.opsly.test.repository

import com.opsly.test.dto.Status
import reactor.core.publisher.Flux

interface FacebookRepository {
    fun get(): Flux<Status>
}