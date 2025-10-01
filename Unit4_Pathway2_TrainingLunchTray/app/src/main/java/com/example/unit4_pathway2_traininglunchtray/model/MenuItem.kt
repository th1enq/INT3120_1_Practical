package com.example.unit4_pathway2_traininglunchtray.model

import java.text.NumberFormat

/**
 * Sealed class representing different types of menu items.
 * Each item has a name, description, and price.
 */
sealed class MenuItem(
    open val name: String,
    open val description: String,
    open val price: Double
) {
    /**
     * Main course item for the meal.
     */
    data class EntreeItem(
        override val name: String,
        override val description: String,
        override val price: Double
    ) : MenuItem(name, description, price)

    /**
     * Side dish item to accompany the main course.
     */
    data class SideDishItem(
        override val name: String,
        override val description: String,
        override val price: Double
    ) : MenuItem(name, description, price)

    /**
     * Additional item like bread or small extras.
     */
    data class AccompanimentItem(
        override val name: String,
        override val description: String,
        override val price: Double
    ) : MenuItem(name, description, price)

    /**
     * Formats the price as currency string.
     * @return Formatted price string (e.g., "$5.50")
     */
    fun getFormattedPrice(): String = NumberFormat.getCurrencyInstance().format(price)
    
    /**
     * Validates that the menu item has valid data.
     * @return true if the item is valid, false otherwise
     */
    fun isValid(): Boolean {
        return name.isNotBlank() && 
               description.isNotBlank() && 
               price >= 0.0
    }
}