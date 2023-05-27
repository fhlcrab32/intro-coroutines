package samples

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main()  = runBlocking {
    val job = launch{
        try {
            repeat(times = 1000) {
                delay(1)
                Thread.sleep(1)
            }
        } catch (ce: CancellationException) {
            println("CancellationException thrown: ${ce.message}")
            withContext(NonCancellable) {
                reportError()
            }
        }
    }
    delay(timeMillis = 1_000)
    job.cancelAndJoin()
}

suspend fun reportError() {
    println("Reporting error")
    try{
        delay(timeMillis = 10) // calling this delay will throw an exception because the
                               // coroutine that calls this suspend
                              // function was already cancelled. We cannot call further suspend functions on it
    } catch (e: Exception) {
        println(e.message)
    }
    println("Reported error")
}
