package com.opsly.test.repository

import com.opsly.test.dto.Status
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class FacebookRestRepositoryTest {

    @Test
    fun get() {
        val facebookStatuses: String = this.javaClass.getResource("/sample-facebook.json").readText()
        val clientResponse: ClientResponse = ClientResponse
                .create(HttpStatus.OK)
                .header("Content-Type","application/json")
                .body(facebookStatuses).build()

        val shortCircuitingExchangeFunction = ExchangeFunction {
            Mono.just(clientResponse)
        }
        val webClientBuilder: WebClient.Builder = WebClient.builder().exchangeFunction(shortCircuitingExchangeFunction)
        val facebookRepository = FacebookRestRepository(webClientBuilder.build(), "facebook")
        val statuses: Flux<Status> = facebookRepository.get()

        StepVerifier
                .create(statuses)
                .expectNext(Status("name1", "status1"))
                .expectNext(Status("name2", "status2"))
                .expectNext(Status("name3", "status3"))
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
        val facebookRepository = FacebookRestRepository(webClientBuilder.build(), "facebook")
        val statuses: Flux<Status> = facebookRepository.get()

        StepVerifier
                .create(statuses)
                .expectComplete()
                .verify()
    }
}