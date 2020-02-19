package com.opsly.test.repository

import com.opsly.test.dto.Photo
import reactor.core.publisher.Flux

interface InstagramRepository {
    fun get(): Flux<Photo>
}