fun calculateTicketCost(age: Int, isMonday: Boolean): Int {
    if (age < 0 || age > 100) {
        return -1
    }
    
    if (age <= 12) {
        return 15
    }
    
    if (age <= 60) {
        return if (isMonday) 25 else 30
    }
    
    return 20
}

fun main() {
    val childAge = 5
    val adultAge = 28
    val seniorAge = 87    
    val mondayDiscount = true
    
    println("The movie ticket price for a person aged $childAge is \$${calculateTicketCost(childAge, mondayDiscount)}.")
    println("The movie ticket price for a person aged $adultAge is \$${calculateTicketCost(adultAge, mondayDiscount)}.")
    println("The movie ticket price for a person aged $seniorAge is \$${calculateTicketCost(seniorAge, mondayDiscount)}.")
}
