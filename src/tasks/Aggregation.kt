package tasks

import contributors.User

fun List<User>.aggregate(): List<User> = this.groupBy {
    user -> user.login
}.map {
    (login, group)  -> User(login, group.sumOf { it.contributions })
}.sortedByDescending {
    it.contributions
}

