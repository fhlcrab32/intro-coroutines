package contributors

val testRequestData = RequestData("username", "password", "org")

data class TestRepo(val name: String, val delay: Long, val users: List<User>)

data class TestResults(val timeFromStart: Long, val users: List<User>)

const val REPOS_DELAY = 1000L

val testRepos = listOf(
    TestRepo(
        "repo-1", 1000, listOf(
            User("user-1", 10),
            User("user-2", 20)
        )
    ),
    TestRepo(
        "repo-2", 1200, listOf(
            User("user-2", 30),
            User("user-1", 40)
        )
    ),
    TestRepo(
        "repo-3", 800, listOf(
            User("user-2", 50),
            User("user-3", 60)
        )
    )
)


val repos = testRepos.mapIndexed { index, testRepo -> Repo(index.toLong(), testRepo.name) }

val reposMap = testRepos.associateBy { it.name }

val expectedResults = TestResults(
    300, // 100 + (100 + 120 + 80)
    listOf(
        User("user-2", 100),
        User("user-3", 60),
        User("user-1", 50)
    )
)

val expectedConcurrentResults = TestResults(
    220, // 100 + max(100, 120, 80)
    expectedResults.users
)

val progressResults = listOf(
    TestResults(
        200, // 100 + 100
        listOf(User(login = "user-2", contributions = 20), User(login = "user-1", contributions = 10))
    ),
    TestResults(
        320, // 200 + 120
        listOf(User(login = "user-2", contributions = 50), User(login = "user-1", contributions = 50))
    ),
    expectedResults
)

val concurrentProgressResults = listOf(
    TestResults(
        180, // 100 + 80
        listOf(User(login = "user-3", contributions = 60), User(login = "user-2", contributions = 50))
    ),
    TestResults(
        200, // 100 + max(80, 100)
        listOf(User(login = "user-2", contributions = 70), User(login = "user-3", contributions = 60),
            User(login = "user-1", contributions = 10))
    ),
    expectedConcurrentResults
)
