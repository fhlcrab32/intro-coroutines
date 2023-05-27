package samples

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

val cachedThreadPoolDispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
val executor = Executors.newFixedThreadPool(10)

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val jobs = arrayListOf<Job>()
    jobs += launch { // the 'default' context
        println("default: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
        println("${this.coroutineContext}")
    }
    jobs += launch (Dispatchers.Default) { // uses fork-join thread pool
        println("Default: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")

    }
    jobs += launch (Dispatchers.IO) { // uses the same thread pool as the default dispatcher
        println("IO: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
    }
    jobs += launch (Dispatchers.Unconfined) { // works with main thread
        println("Unconfined: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
    }
    jobs += launch (newSingleThreadContext("OwnThread")) { // launches its own thread
        println("Single thread context: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
    }
    jobs += launch (cachedThreadPoolDispatcher) { // dispatched to ForkJoinPool.commpnPool, or equivalent
        println("Cached thread pool: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
    }
    jobs += launch (executor.asCoroutineDispatcher()) { // dispatched to ForkJoinPool.commonPool, or equivalent
        println("Fixed thread pool: In thread ${Thread.currentThread().name}: ${this.coroutineContext}")
    }
    jobs.forEach { it.join() }
    Thread.sleep(100)
    executor.shutdown()
    cachedThreadPoolDispatcher.close()
}
