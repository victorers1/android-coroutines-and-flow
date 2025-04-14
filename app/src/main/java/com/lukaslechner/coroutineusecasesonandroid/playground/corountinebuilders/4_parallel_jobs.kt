package com.lukaslechner.coroutineusecasesonandroid.playground.corountinebuilders

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
fun main() = runBlocking<Unit> {

    // Shared mutable state
    // This should be avoided whenever possible as it can lead to
    // racing problems
    var resultList = mutableListOf<String>()

    val startTime = System.currentTimeMillis()

    val job1 = launch {
        val result1 = networkCall(1) // makes the function suspend
        resultList.add(result1)
        println("result1 received: \'$result1\' after ${elapsedMillis(startTime)}ms")
    }

    val job2 = launch {
        val result2 = networkCall(2) // makes the function suspend
        resultList.add(result2)
        println("result2 received: \'$result2\' after ${elapsedMillis(startTime)}ms")
    }

    job1.join()
    job2.join()

    println("Result list: \'$resultList\' after ${elapsedMillis(startTime)}ms")
}

suspend fun networkCall(number: Int): String {
    delay(500)
    return "Result $number"
}

fun elapsedMillis(startTime: Long) = System.currentTimeMillis() - startTime