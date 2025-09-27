fun handleTrafficLight(color: String): String {
    return if (color == "Red") {
        "Stop"
    } else if (color == "Yellow" || color == "Amber") {
        "Slow"
    } else if (color == "Green") {
        "Go"
    } else {
        "Invalid traffic-light color"
    }
}

fun main() {
    val lightColor = "Black"
    
    val action = handleTrafficLight(lightColor)
    println(action)
}
