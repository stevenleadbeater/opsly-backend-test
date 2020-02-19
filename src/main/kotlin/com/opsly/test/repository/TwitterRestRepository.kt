package com.opsly.test.repository

import com.opsly.test.dto.Tweet
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class TwitterRestRepository(
        private val client: WebClient,
        @Value("\${twitter.uri}") private val uri: String
) : TwitterRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun get(): Flux<Tweet> = client
            .get()
            .uri(uri)
            .retrieve()
            .bodyToFlux<Tweet>()
            .onErrorResume {
                logger.error("Cannot retrieve twitter data", it)
                Mono.empty()
            }
}