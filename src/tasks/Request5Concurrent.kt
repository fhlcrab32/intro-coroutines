package tasks

import contributors.*
import kotlinx.coroutines.*


private const val DELAY = 3000L

suspend fun loadContributorsConcurrent(service: GitHubService, req: RequestData): List<User> = coroutineScope {
    val repos =  service.getOrgRepos(req.org)
        .also { logRepos(req, it) }
        .bodyList()
    val deferreds = repos.map { repo ->
        async {
            log("starting loading for ${repo.name}")
            delay(DELAY)
            service.getRepoContributors(req.org, repo.name)
                .also { logUsers(repo, it) }
                .bodyList()
        }
    }
    deferreds.awaitAll().flatten().aggregate()
}
