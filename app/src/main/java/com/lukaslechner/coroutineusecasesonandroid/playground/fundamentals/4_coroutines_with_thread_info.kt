package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    println("main starts")

    joinAll( // equivalent to a for-each Job
        async { coroutineThreadInfo(1, 500) },
        async { coroutineThreadInfo(2, 300) }
    )

    println("main ends")
}

// Behaves like it runs on different threads but runs in a single thread
suspend fun coroutineThreadInfo(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}