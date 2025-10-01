package com.example.unit4_pathway2_traininglunchtray.util

import com.example.unit4_pathway2_traininglunchtray.model.MenuItem
import com.example.unit4_pathway2_traininglunchtray.model.OrderUiState

/**
 * Utility object for validating menu items and orders.
 */
object ValidationUtils {
    
    /**
     * Validates a list of menu items to ensure they are all valid.
     * @param items List of menu items to validate
     * @return true if all items are valid, false otherwise
     */
    fun validateMenuItems(items: List<MenuItem>): Boolean {
        return items.isNotEmpty() && items.all { it.isValid() }
    }
    
    /**
     * Validates an order state to ensure it has valid data.
     * @param orderState The order state to validate
     * @return true if the order is valid, false otherwise
     */
    fun validateOrder(orderState: OrderUiState): Boolean {
        return orderState.hasItems && 
               orderState.itemTotalPrice >= 0.0 &&
               orderState.orderTax >= 0.0 &&
               orderState.orderTotalPrice >= 0.0
    }
    
    /**
     * Validates that all required items are selected for a complete order.
     * @param orderState The order state to validate
     * @return true if all required items are present, false otherwise
     */
    fun validateCompleteOrder(orderState: OrderUiState): Boolean {
        return orderState.isComplete && validateOrder(orderState)
    }
    
    /**
     * Checks if a price is valid (non-negative and reasonable).
     * @param price The price to validate
     * @return true if the price is valid, false otherwise
     */
    fun isValidPrice(price: Double): Boolean {
        return price >= 0.0 && price <= 1000.0 // Max reasonable price
    }
    
    /**
     * Checks if a menu item name is valid.
     * @param name The name to validate
     * @return true if the name is valid, false otherwise
     */
    fun isValidName(name: String): Boolean {
        return name.isNotBlank() && name.length <= 100
    }
    
    /**
     * Checks if a menu item description is valid.
     * @param description The description to validate
     * @return true if the description is valid, false otherwise
     */
    fun isValidDescription(description: String): Boolean {
        return description.isNotBlank() && description.length <= 500
    }
}