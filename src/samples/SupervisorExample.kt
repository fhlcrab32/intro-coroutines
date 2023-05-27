package samples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope


fun main() = runBlocking<Unit> {
    supervisorScope {
        launch {
            repeat(times = 10) {
                doWork1(1)
            }

        }
        launch {
            repeat(times = 10) {
                doWork1(2)
            }
        }
        // the parent of this coroutine is SupervisorJob
        launch {
            delay(timeMillis = 2000)
            throw IllegalArgumentException("some exception")
        }
        delay(timeMillis = 5000)
    }
}

suspend fun doWork1(i: Int) {
    print(i)
    delay(timeMillis = 10)
}

