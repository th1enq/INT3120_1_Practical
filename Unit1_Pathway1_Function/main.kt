fun birthdayGreeting(name: String = "Default name", age: Int): String {
    val nameGreeting = "Happy birthday, $name!"
    val ageGreeting = "You are now $age years old!"
    return "$nameGreeting\n$ageGreeting"
}

fun main() {    
    println(birthdayGreeting("hathu", 21))
    println(birthdayGreeting(age = 20, name = "th1enq"))
    println(birthdayGreeting(age = 22))
}