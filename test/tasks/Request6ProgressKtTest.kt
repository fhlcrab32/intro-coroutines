@file:OptIn(ExperimentalCoroutinesApi::class)

package tasks

import contributors.MockGithubService
import contributors.expectedConcurrentResults
import contributors.log
import contributors.progressResults
import contributors.testRequestData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class Request6ProgressKtTest {
    @Test
    fun testProgress() = runTest {
        val startTime = System.currentTimeMillis()
        var index = 0
        loadContributorsProgress(MockGithubService, testRequestData) {
            users, _ ->
            val expected = progressResults[index++]
            val time = System.currentTimeMillis() - startTime
            log("Expected time:  < ${expectedConcurrentResults.timeFromStart 
                    + 500} , Actual time: $time")
            Assert.assertTrue("Expected intermediate result after virtual ${expected.timeFromStart} ms:",
                time  < expected.timeFromStart + 500)
            Assert.assertEquals("Wrong intermediate result after $time:", expected.users, users)
        }
    }
}
