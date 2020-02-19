package com.opsly.test.repository

import com.opsly.test.dto.Photo
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class InstagramRestRepositoryTest {

    @Test
    fun get() {
        val instagramStatuses: String = this.javaClass.getResource("/sample-instagram.json").readText()
        val clientResponse: ClientResponse = ClientResponse
                .create(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(instagramStatuses).build()

        val shortCircuitingExchangeFunction = ExchangeFunction {
            Mono.just(clientResponse)
        }
        val webClientBuilder: WebClient.Builder = WebClient.builder().exchangeFunction(shortCircuitingExchangeFunction)
        val instagramRepository = InstagramRestRepository(webClientBuilder.build(), "instagram")
        val statuses: Flux<Photo> = instagramRepository.get()

        StepVerifier
                .create(statuses)
                .expectNext(Photo("username1", "picture1"))
                .expectNext(Photo("username2", "picture2"))
                .expectNext(Photo("username3", "picture3"))
                .expectComplete()
                .verify()
    }

    @Test
    fun get_error() {
        val clientResponse: ClientResponse = ClientResponse
                .create(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-Type","text/utf-8")
                .body("Some not JSON response").build()

        val shortCircuitingExchangeFunction = ExchangeFunction {
            Mono.just(clientResponse)
        }
        val webClientBuilder: WebClient.Builder = WebClient.builder().exchangeFunction(shortCircuitingExchangeFunction)
        val instagramRepository = InstagramRestRepository(webClientBuilder.build(), "instagram")
        val statuses: Flux<Photo> = instagramRepository.get()

        StepVerifier
                .create(statuses)
                .expectComplete()
                .verify()
    }
}