package com.opsly.test.dto

data class AggregateResult(
        var twitter: MutableList<Tweet>?,
        var facebook: MutableList<Status>?,
        var instagram: MutableList<Photo>?
)