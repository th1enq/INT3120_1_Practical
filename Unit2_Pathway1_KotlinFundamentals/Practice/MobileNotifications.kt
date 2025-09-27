fun printNotifications(numberOfNotifications: Int) {
    if (numberOfNotifications < 100) {
        println("You have $(numberOfNotifictions) notifications.")
    } else {
        println("Your phone is blowing up! You have 99+ notifications.")
    }
}

fun main() {
    var morningNotifications = 55
    var eveningNotifications = 140  

    printNotifications(morningNotifications)
    printNotifications(eveningNotifications)
}       