package samples

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


fun main() = runBlocking {

    val job = launch {
        repeat(times = 1000) {
            print(".")
            delay(timeMillis = 10)
        }
    }
    delay(timeMillis = 250)
    job.cancelAndJoin()
    print("Done")
}
