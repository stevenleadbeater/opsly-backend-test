package com.opsly.test.repository

import com.opsly.test.dto.Tweet
import reactor.core.publisher.Flux

interface TwitterRepository {
    fun get(): Flux<Tweet>
}