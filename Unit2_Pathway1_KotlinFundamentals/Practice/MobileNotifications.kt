fun displayNotificationCount(notificationCount: Int) {
    val message = when {
        notificationCount >= 100 -> "Your phone is blowing up! You have 99+ notifications."
        else -> "You have $notificationCount notifications."
    }
    println(message)
}

fun main() {
    val morningCount = 55
    val eveningCount = 140  

    displayNotificationCount(morningCount)
    displayNotificationCount(eveningCount)
}           