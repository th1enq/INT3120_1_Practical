fun main() {
    var favoriteActor: String? = "Kim Ji Won"

    var lengthOfName = favoriteActor?.length ?: 0

    println("The number of characters in your favorite actor's name is $lengthOfName.")
}