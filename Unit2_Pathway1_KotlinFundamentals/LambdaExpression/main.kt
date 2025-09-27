fun executeAction(isTrick: Boolean, bonusReward: ((Int) -> String)? = null) {
    val trickAction = { println("No treats!") }
    val treatAction = { println("Have a treat!") }
    
    if (isTrick) {
        trickAction()
    } else {
        bonusReward?.let { reward -> println(reward(5)) }
        treatAction()
    }
}

fun main() {
    val coinReward: (Int) -> String = { amount -> "$amount quarters " }

    repeat(4) {
        executeAction(false, coinReward)
    }   
    executeAction(true)
}