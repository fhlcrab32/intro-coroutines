@file:OptIn(DelicateCoroutinesApi::class)

package samples

import kotlinx.coroutines.delay
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking



fun main() = runBlocking<Unit> {
    launch {
        runWithGlobalScope()
        println("runWithGlobalScope returned")
    }
}

private suspend fun runWithGlobalScope() {
    GlobalScope.launch {
        println("Launch 1")
        delay(timeMillis = 1000)
    }

    GlobalScope.launch {
        println("Launch 2")
        delay(timeMillis = 2000)
    }
    println("runWithGlobalScope completed")
}
