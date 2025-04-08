package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

/// Runs in a single thread, no coroutines involved
fun main() {
    println("main starts")

    routine(1, 500)

    routine(2, 300) // Executed only when the above command finishes

    println("main ends")
}

fun routine(number: Int, delay: Long) {
    println("Coroutine $number starts work")
    Thread.sleep(delay) // Blocks current thread
    println("Coroutine $number has finished")
}