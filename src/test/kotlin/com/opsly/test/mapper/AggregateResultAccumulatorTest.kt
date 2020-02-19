package com.opsly.test.mapper

import com.opsly.test.dto.AggregateResult
import com.opsly.test.dto.Photo
import com.opsly.test.dto.Status
import com.opsly.test.dto.Tweet
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class AggregateResultAccumulatorTest {

    @Test
    fun accumulate_all_empty_add_status() {
        val aggregateResultAccumulator = AggregateResultAccumulator()
        var aggregateResult = AggregateResult(ArrayList(), ArrayList(), ArrayList())
        aggregateResultAccumulator.accumulate(aggregateResult, Status("name", "status"))
        assertThat(aggregateResult.facebook).hasSize(1)
        assertThat(aggregateResult.instagram).hasSize(0)
        assertThat(aggregateResult.twitter).hasSize(0)
        assertThat(aggregateResult.facebook?.get(0)?.name).isEqualTo("name")
        assertThat(aggregateResult.facebook?.get(0)?.status).isEqualTo("status")
    }

    @Test
    fun accumulate_all_empty_add_photo() {
        val aggregateResultAccumulator = AggregateResultAccumulator()
        var aggregateResult = AggregateResult(ArrayList(), ArrayList(), ArrayList())
        aggregateResultAccumulator.accumulate(aggregateResult, Photo("username", "picture"))
        assertThat(aggregateResult.facebook).hasSize(0)
        assertThat(aggregateResult.instagram).hasSize(1)
        assertThat(aggregateResult.twitter).hasSize(0)
        assertThat(aggregateResult.instagram?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.instagram?.get(0)?.picture).isEqualTo("picture")
    }

    @Test
    fun accumulate_all_empty_add_tweet() {
        val aggregateResultAccumulator = AggregateResultAccumulator()
        var aggregateResult = AggregateResult(ArrayList(), ArrayList(), ArrayList())
        aggregateResultAccumulator.accumulate(aggregateResult, Tweet("username", "tweet"))
        assertThat(aggregateResult.facebook).hasSize(0)
        assertThat(aggregateResult.instagram).hasSize(0)
        assertThat(aggregateResult.twitter).hasSize(1)
        assertThat(aggregateResult.twitter?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.twitter?.get(0)?.tweet).isEqualTo("tweet")
    }

    @Test
    fun accumulate_all_empty_add_one_of_each() {
        val aggregateResultAccumulator = AggregateResultAccumulator()
        var aggregateResult = AggregateResult(ArrayList(), ArrayList(), ArrayList())
        aggregateResultAccumulator.accumulate(aggregateResult, Tweet("username", "tweet"))
        aggregateResultAccumulator.accumulate(aggregateResult, Photo("username", "picture"))
        aggregateResultAccumulator.accumulate(aggregateResult, Status("name", "status"))
        assertThat(aggregateResult.facebook).hasSize(1)
        assertThat(aggregateResult.instagram).hasSize(1)
        assertThat(aggregateResult.twitter).hasSize(1)
        assertThat(aggregateResult.twitter?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.twitter?.get(0)?.tweet).isEqualTo("tweet")
        assertThat(aggregateResult.instagram?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.instagram?.get(0)?.picture).isEqualTo("picture")
        assertThat(aggregateResult.facebook?.get(0)?.name).isEqualTo("name")
        assertThat(aggregateResult.facebook?.get(0)?.status).isEqualTo("status")
    }

    @Test
    fun accumulate_all_empty_add_two_of_each() {
        val aggregateResultAccumulator = AggregateResultAccumulator()
        var aggregateResult = AggregateResult(ArrayList(), ArrayList(), ArrayList())
        aggregateResultAccumulator.accumulate(aggregateResult, Tweet("username", "tweet"))
        aggregateResultAccumulator.accumulate(aggregateResult, Photo("username", "picture"))
        aggregateResultAccumulator.accumulate(aggregateResult, Status("name", "status"))
        aggregateResultAccumulator.accumulate(aggregateResult, Tweet("username2", "tweet2"))
        aggregateResultAccumulator.accumulate(aggregateResult, Photo("username2", "picture2"))
        aggregateResultAccumulator.accumulate(aggregateResult, Status("name2", "status2"))
        assertThat(aggregateResult.facebook).hasSize(2)
        assertThat(aggregateResult.instagram).hasSize(2)
        assertThat(aggregateResult.twitter).hasSize(2)
        assertThat(aggregateResult.twitter?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.twitter?.get(0)?.tweet).isEqualTo("tweet")
        assertThat(aggregateResult.instagram?.get(0)?.username).isEqualTo("username")
        assertThat(aggregateResult.instagram?.get(0)?.picture).isEqualTo("picture")
        assertThat(aggregateResult.facebook?.get(0)?.name).isEqualTo("name")
        assertThat(aggregateResult.facebook?.get(0)?.status).isEqualTo("status")
        assertThat(aggregateResult.twitter?.get(1)?.username).isEqualTo("username2")
        assertThat(aggregateResult.twitter?.get(1)?.tweet).isEqualTo("tweet2")
        assertThat(aggregateResult.instagram?.get(1)?.username).isEqualTo("username2")
        assertThat(aggregateResult.instagram?.get(1)?.picture).isEqualTo("picture2")
        assertThat(aggregateResult.facebook?.get(1)?.name).isEqualTo("name2")
        assertThat(aggregateResult.facebook?.get(1)?.status).isEqualTo("status2")
    }
}