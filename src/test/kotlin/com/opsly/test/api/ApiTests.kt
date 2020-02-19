package com.opsly.test.api

import com.opsly.test.dto.AggregateResult
import io.restassured.RestAssured.get
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
class ApiTests {
    @Test
    fun get() {
        val result = get("http://localhost:3000")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .`as`<AggregateResult>(AggregateResult::class.java)
        assertThat(result.instagram).isNotNull
        assertThat(result.facebook).isNotNull
        assertThat(result.twitter).isNotNull
    }
}