package com.opsly.test.repository

import com.opsly.test.dto.Photo
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class InstagramRestRepository(
        private val client: WebClient,
        @Value("\${instagram.uri}") private val uri: String
) : InstagramRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun get(): Flux<Photo> = client
            .get()
            .uri(uri)
            .retrieve()
            .bodyToFlux<Photo>()
            .onErrorResume {
                logger.error("Cannot retrieve instagram data", it)
                Mono.empty()
            }
}