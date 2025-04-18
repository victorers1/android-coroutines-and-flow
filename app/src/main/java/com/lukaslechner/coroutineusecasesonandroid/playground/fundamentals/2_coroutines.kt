package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {
    println("main starts")

    // Register all coroutines to run in parallel.
    // Because of that, the run time is 500 ms
    joinAll(
        async { coroutine(1, 500) },
        async { coroutine(2, 300) }
    )

    println("main ends")
}

suspend fun coroutine(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}