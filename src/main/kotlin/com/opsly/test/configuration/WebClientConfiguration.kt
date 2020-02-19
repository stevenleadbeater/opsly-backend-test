package com.opsly.test.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfiguration {
    @Bean
    fun webClient(
            @Value("\${base.address}") address: String
    ): WebClient = WebClient.create(address)
}