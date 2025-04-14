package com.lukaslechner.coroutineusecasesonandroid.playground.corountinebuilders

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@DelicateCoroutinesApi
fun main() = runBlocking<Unit> {
    val job = launch {
        delay(500)
        println("printed from within Coroutine")

        // Because of how launch works, we don't have access to the
        // return value of the coroutine.
       jobRequest()
    }

    job.join() // await until job is done

    println("main ends")
}

suspend fun jobRequest(): String {
    delay(500)
    return "Result"
}