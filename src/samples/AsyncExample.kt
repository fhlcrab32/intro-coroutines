package samples

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main()  = runBlocking {
    var timeTaken = 0L
    val job1 = launch {
         timeTaken = measureTimeMillis {
             doWork1()
             doWork2()
         }
    }
    job1.join()
    println("Time taken without async: $timeTaken")
    timeTaken = measureTimeMillis {
        val deferred1 = async { doWork1() }
        val deferred2 = async { doWork2() }
        deferred1.await()
        deferred2.await()
    }
    println("Time taken with async: $timeTaken")
}

private suspend fun doWork1(): Int {
    delay(timeMillis = 500)
    return Random(System.currentTimeMillis()).nextInt()
}

private suspend fun doWork2(): Int {
    delay(timeMillis = 500)
    return Random(System.currentTimeMillis()).nextInt()
}
