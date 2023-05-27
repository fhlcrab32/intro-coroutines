package samples

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main()  = runBlocking{
    val parentJob = Job() // for using this as the parent in a new launch
    val scope = CoroutineScope(Job())
    val job = scope.launch(parentJob) {
        println("parent: $parentJob")
        println("this job: ${coroutineContext.job}")
        println("this job2: ${coroutineContext[Job]}")
    }
    job.join().also { println("launched job: $job") }
    println("Children")
}
