val trick = {
    println("No treats!")
}

val treat = {
    println("Have a treat!")
}

fun trickOrTreat(isTrick: Boolean, extraTreat: ((Int) -> String)?): () -> Unit {
    if (isTrick) {
        return trick
    } else {
        if (extraTreat != null) {
            println(extraTreat(5))
        }
        return treat
    }
}

fun main() {
    // val coins: (Int) -> String = { quantity ->
    //     "$quantity quarters"
    // }

    val treatFunction = trickOrTreat(false, { "$it quarters "})
    val trickFunction = trickOrTreat(true, null)
    repeat(4) {
        treatFunction()
    }   
    trickFunction()
}