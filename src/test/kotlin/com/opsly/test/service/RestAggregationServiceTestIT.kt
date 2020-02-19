package com.opsly.test.service

import com.opsly.test.dto.AggregateResult
import com.opsly.test.dto.Photo
import com.opsly.test.dto.Status
import com.opsly.test.dto.Tweet
import com.opsly.test.mapper.AggregateResultAccumulator
import com.opsly.test.repository.FacebookRepository
import com.opsly.test.repository.InstagramRepository
import com.opsly.test.repository.TwitterRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.mockito.Mockito
import reactor.core.publisher.Flux

internal class RestAggregationServiceTestIT {

    private val facebookRepository = Mockito.mock(FacebookRepository::class.java)
    private val instagramRepository = Mockito.mock(InstagramRepository::class.java)
    private val twitterRepository = Mockito.mock(TwitterRepository::class.java)
    private val restAggregationService = RestAggregationService(facebookRepository, instagramRepository, twitterRepository, AggregateResultAccumulator())

    @Test
    fun get_empties() {
        Mockito.`when`(facebookRepository.get()).thenReturn(Flux.empty())
        Mockito.`when`(instagramRepository.get()).thenReturn(Flux.empty())
        Mockito.`when`(twitterRepository.get()).thenReturn(Flux.empty())
        val result = restAggregationService.get().block()
        assertThat(result?.twitter).hasSize(0)
        assertThat(result?.facebook).hasSize(0)
        assertThat(result?.instagram).hasSize(0)
    }

    @Test
    fun get_singles() {
        Mockito.`when`(facebookRepository.get()).thenReturn(Flux.just(Status("name", "status")))
        Mockito.`when`(instagramRepository.get()).thenReturn(Flux.just(Photo("username", "picture")))
        Mockito.`when`(twitterRepository.get()).thenReturn(Flux.just(Tweet("username", "tweet")))
        val result = restAggregationService.get().block()
        assertThat(result?.twitter).hasSize(1)
        assertThat(result?.facebook).hasSize(1)
        assertThat(result?.instagram).hasSize(1)
    }

    @Test
    fun get_multiples() {
        Mockito.`when`(facebookRepository.get()).thenReturn(Flux.fromArray(arrayOf(Status("name1", "status1"), Status("name2", "status2"))))
        Mockito.`when`(instagramRepository.get()).thenReturn(Flux.fromArray(arrayOf(Photo("username1", "picture1"), Photo("username2", "picture2"))))
        Mockito.`when`(twitterRepository.get()).thenReturn(Flux.fromArray(arrayOf(Tweet("username1", "tweet1"), Tweet("username2", "tweet2"))))
        val result = restAggregationService.get().block()
        assertThat(result?.twitter).hasSize(2)
        assertThat(result?.facebook).hasSize(2)
        assertThat(result?.instagram).hasSize(2)
    }
}