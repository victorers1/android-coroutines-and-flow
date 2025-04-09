package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() = runBlocking {
    println("main starts")

    joinAll( // equivalent to a for-each Job
        async { multiThreadCoroutine(1, 500) },
        async { multiThreadCoroutine(2, 300) }
    )

    println("main ends")
}

// Behaves like it runs on different threads but runs in a single thread
suspend fun multiThreadCoroutine(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)

    withContext(Dispatchers.Default) {
        println("Coroutine $number has finished on ${Thread.currentThread().name}")
    }

}