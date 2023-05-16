package contributors

fun main() {
    setDefaultFontSize(size = 18f)
    ContributorsUI().apply {
        pack()
        setLocationRelativeTo(null)
        isVisible = true
    }
}
