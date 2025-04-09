package com.lukaslechner.coroutineusecasesonandroid.playground.corountinebuilders

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
fun main() {

    // It's a non-blocking coroutine.
    // The body will start to run, but the delay(500) will make the program
    // jump to the next line out of the launch{} for the next 500 ms
    GlobalScope.launch {
        delay(500)
        println("printed from within Coroutine")
    }

    Thread.sleep(1000) // Without this, the above println won't be executed
    println("main ends")
}