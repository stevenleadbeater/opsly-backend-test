package com.opsly.test.mapper

import com.opsly.test.dto.AggregateResult
import com.opsly.test.dto.Photo
import com.opsly.test.dto.Status
import com.opsly.test.dto.Tweet
import org.springframework.stereotype.Component

@Component
class AggregateResultAccumulator {

    fun accumulate(last: AggregateResult, current: Any): AggregateResult {
        if (current is Tweet) {
            last.twitter?.add(current)
        }
        if (current is Status) {
            last.facebook?.add(current)
        }
        if (current is Photo) {
            last.instagram?.add(current)
        }
        return last
    }
}