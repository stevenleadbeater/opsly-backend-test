package com.opsly.test.routing

import com.opsly.test.service.AggregationService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router

@Configuration
class AggregateRoutingConfiguration {
    @Bean
    fun routerFunction(aggregationService: AggregationService): RouterFunction<ServerResponse> = router {
        ("/").nest {
            GET("/") {
                ServerResponse.ok().body(aggregationService.get())
            }
        }
    }
}