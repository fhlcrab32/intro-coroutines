package tasks

import contributors.MockGithubService
import contributors.expectedConcurrentResults
import contributors.log
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Request5ConcurrentKtTest {
    @Test
    fun testConcurrent() = runTest {
        val startTime = System.currentTimeMillis()
        val result = loadContributorsConcurrent(MockGithubService, testRequestData)
        Assert.assertEquals("Wrong result for 'loadContributorsConcurrent'", expectedConcurrentResults.users, result)
        val totalTime = System.currentTimeMillis() - startTime
        log("Expected time:  < ${expectedConcurrentResults.timeFromStart + 500} , " +
                "Actual time: $totalTime")
        Assert.assertTrue(
            "The calls run concurrently, so the total virtual time should be 220 ms: " +
                    "100 ms for repos request plus max(100, 120, 80) = 120 ms for concurrent contributors " +
                    "requests)",
            totalTime  < expectedConcurrentResults.timeFromStart + 500
        )
    }
}
