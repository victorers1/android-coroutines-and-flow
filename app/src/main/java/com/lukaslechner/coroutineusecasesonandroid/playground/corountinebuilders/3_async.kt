package com.lukaslechner.coroutineusecasesonandroid.playground.corountinebuilders

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
fun main() = runBlocking {
    println("main starts")

    async { coroutine(1, 500) }
    async { coroutine(2, 300) }

    println("main ends")
}

suspend fun coroutine(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    delay(delay)
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}