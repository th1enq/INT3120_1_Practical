fun getNameLength(actorName: String?): Int {
    return if (actorName != null) {
        actorName.length
    } else {
        0
    }
}

fun main() {
    val favoriteActor: String? = "Kim Ji Won"

    val nameLength = getNameLength(favoriteActor)

    println("The number of characters in your favorite actor's name is $nameLength.")
}