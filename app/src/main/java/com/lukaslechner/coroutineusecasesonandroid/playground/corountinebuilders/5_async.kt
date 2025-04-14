package com.lukaslechner.coroutineusecasesonandroid.playground.corountinebuilders

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
fun main() = runBlocking<Unit> {

    val startTime = System.currentTimeMillis()

    val deferred1 = async {
        val result1 = networkAsync(1)
        println("result1 received: \'$result1\' after ${elapsedMillis(startTime)}ms")
        result1
    }

    val deferred2 = async {
        val result2 = networkAsync(2)
        println("result2 received: \'$result2\' after ${elapsedMillis(startTime)}ms")
        result2
    }

    val resultList = listOf<String>(
        deferred1.await(),
        deferred2.await()
    )

    println("Result list: \'$resultList\' after ${elapsedMillis(startTime)}ms")
}

suspend fun networkAsync(number: Int): String {
    delay(500)
    return "Result $number"
}
