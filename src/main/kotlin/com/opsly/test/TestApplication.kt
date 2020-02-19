package com.opsly.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(/*scanBasePackages = ["com.opsly.test"]*/)
class TestApplication

fun main(args: Array<String>) {
    runApplication<TestApplication>(*args)
}
