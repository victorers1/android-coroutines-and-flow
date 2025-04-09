package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

/// Runs in a single thread, no coroutines involved
fun main() {
    println("main starts")

    routine(1, 500)

    routine(2, 300)

    println("main ends")
}

// Doesn't create new threads or coroutines. Blocks the main thread, where it's running
fun routine(number: Int, delay: Long) {
    println("Coroutine $number starts work on ${Thread.currentThread().name}")
    Thread.sleep(delay) // Blocks current thread
    println("Coroutine $number has finished on ${Thread.currentThread().name}")
}