package samples

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

private val exceptionHandler = CoroutineExceptionHandler { context, exception ->
        println("\nException: ${exception.message}") }
private val grandParentScope = CoroutineScope(Job() + exceptionHandler)

fun main() = runBlocking<Unit> {
    val job = grandParentScope.launch {
        supervisorScope {
            launch { repeat(times = 10) { doWork2(1) } }
            launch { repeat(times = 10) { doWork2(2) } }
            // the parent of this coroutine is SupervisorJob
            launch {
                delay(timeMillis = 1000)
                throw IllegalArgumentException("some exception")
            }
            delay(timeMillis = 5000)
        }
    }
    job.join()
}

private suspend fun doWork2(i: Int) {
    print(i)
    delay(timeMillis = 10)
}

