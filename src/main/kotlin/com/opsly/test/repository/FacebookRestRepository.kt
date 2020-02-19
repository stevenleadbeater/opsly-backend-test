package com.opsly.test.repository

import com.opsly.test.dto.Status
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class FacebookRestRepository(
        private val client: WebClient,
        @Value("\${facebook.uri}") private val uri: String
) : FacebookRepository {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun get(): Flux<Status> = client
            .get()
            .uri(uri)
            .retrieve()
            .bodyToFlux<Status>()
            .onErrorResume {
                logger.error("Cannot retrieve facebook data", it)
                Mono.empty()
            }
}