package samples

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking<Unit> {
    launch {
        repeat(times = 10) {
            doWork(1)
        }

    }
    launch {
        repeat(times = 10) {
            doWork(2)
        }
    }
    delay(timeMillis = 100)
    throw Exception("some exception")
}

private suspend fun doWork(i: Int) {
    print(i)
    delay(timeMillis = 10)
}

