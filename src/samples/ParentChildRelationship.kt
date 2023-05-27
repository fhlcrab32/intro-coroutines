package samples

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield


fun main(args: Array<String>) = runBlocking {
    val parentJob = Job() // can be used as the inherited scope to run a coroutine later
    val scope = CoroutineScope(Job())
    val job = scope.launch(parentJob) {
        println("Job passed to the scope.launch as the new context: $parentJob")
        println("Current context: ${coroutineContext[Job]}") //coroutineContext[Job] = coroutineContext.job
        println("Children of launched Job: ${parentJob.children.joinToString(", ")}")
    }
    yield()
    println("launched job: $job")
    displayChildren(job = parentJob)
    job.join()


}

private fun displayChildren(depth: Int = 0, job: Job) {
    println("Children of $job: ")
    job.children.forEach {
        for (i in 0..depth) {
            print("\t")
        }
        println(it)
        displayChildren(depth + 1, it)
    }
}

