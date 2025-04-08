package com.lukaslechner.coroutineusecasesonandroid.playground.fundamentals

import kotlin.concurrent.thread

fun main() {
    println("main starts")

    threadRoutine(1, 500)
    threadRoutine(2, 300)

    Thread.sleep(1000)

    println("main ends")
}

fun threadRoutine(number: Int, delay: Long) {
    thread { // Creates a new thread
        println("Coroutine $number starts work")
        Thread.sleep(delay)
        println("Coroutine $number has finished")
    }

}