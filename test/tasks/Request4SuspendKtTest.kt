package tasks

import contributors.MockGithubService
import contributors.expectedResults
import contributors.log
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class Request4SuspendKtTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testSuspend() = runTest {
        val startTime = System.currentTimeMillis()
        val result = testLoadContributorsSuspend()
        Assert.assertEquals("Wrong result for 'loadContributorsSuspend'", expectedResults.users, result)
        val totalTime = System.currentTimeMillis() - startTime
        log("Expected time: ${expectedResults.timeFromStart} - ${expectedResults.timeFromStart + 500} , " +
                "Actual time: $totalTime")
        Assert.assertTrue(
            "The calls run consequently, so the total time should be around 400 ms: " +
                    "100 for repos request plus (100 + 120 + 80) = 300 for sequential contributors requests)",
            totalTime < expectedResults.timeFromStart + 500
        )
    }

    private suspend fun testLoadContributorsSuspend() = coroutineScope {
            loadContributorsSuspend(MockGithubService, testRequestData)
    }
}
