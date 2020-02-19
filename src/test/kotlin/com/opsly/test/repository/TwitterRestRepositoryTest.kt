package com.opsly.test.repository

import com.opsly.test.dto.Tweet
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class TwitterRestRepositoryTest {

    @Test
    fun get() {
        val twitterStatuses: String = this.javaClass.getResource("/sample-twitter.json").readText()
        val clientResponse: ClientResponse = ClientResponse
                .create(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(twitterStatuses).build()

        val shortCircuitingExchangeFunction = ExchangeFunction {
            Mono.just(clientResponse)
        }
        val webClientBuilder: WebClient.Builder = WebClient.builder().exchangeFunction(shortCircuitingExchangeFunction)
        val twitterRepository = TwitterRestRepository(webClientBuilder.build(), "twitter")
        val statuses: Flux<Tweet> = twitterRepository.get()

        StepVerifier
                .create(statuses)
                .expectNext(Tweet("username1", "tweet1"))
                .expectNext(Tweet("username2", "tweet2"))
                .expectNext(Tweet("username3", "tweet3"))
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
        val twitterRepository = TwitterRestRepository(webClientBuilder.build(), "twitter")
        val statuses: Flux<Tweet> = twitterRepository.get()

        StepVerifier
                .create(statuses)
                .expectComplete()
                .verify()
    }
}