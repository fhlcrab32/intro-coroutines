package samples

class LazyCoroutineExample {
    val children by lazy {  loadChildren() }

    companion object {
        fun loadChildren() = listOf("Adam", "Ben", "Corey")
    }
}

fun main() {
        LazyCoroutineExample().children.forEach  ( ::println )
}
