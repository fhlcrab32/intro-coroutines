package samples

import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main()  = runBlocking{
    val deferredObject = async {
        delay(timeMillis = 1000)
        "42"
    }
    deferredObject.cancelAndJoin()
    /* without the check isCancelled, this will throw a JobCancellationException
     * as the coroutine was already cancelled
     * */
    if (!deferredObject.isCancelled) {
        println(deferredObject.await())
    }
}
