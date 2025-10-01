package com.example.unit4_pathway2_trainingcupcake.ui

import androidx.lifecycle.ViewModel
import com.example.unit4_pathway2_trainingcupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Constants for order pricing and configuration
 */
private object OrderConstants {
    /** Price for a single cupcake in USD */
    const val PRICE_PER_CUPCAKE = 2.00
    
    /** Additional cost for same day pickup of an order in USD */
    const val PRICE_FOR_SAME_DAY_PICKUP = 3.00
    
    /** Number of pickup date options to generate */
    const val PICKUP_OPTIONS_COUNT = 4
    
    /** Date format pattern for pickup options */
    const val DATE_FORMAT_PATTERN = "E MMM d"
}

/**
 * ViewModel that manages cupcake order state and business logic.
 * 
 * Handles quantity selection, flavor choice, pickup date, and price calculations.
 * Provides a single source of truth for order-related data using StateFlow.
 */
class OrderViewModel : ViewModel() {

    /**
     * Private mutable state for the cupcake order
     */
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = generatePickupOptions()))
    
    /**
     * Public read-only state flow for observing order state changes
     */
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Updates the quantity of cupcakes for this order and recalculates the price.
     * 
     * @param numberCupcakes The number of cupcakes to order (must be positive)
     */
    fun setQuantity(numberCupcakes: Int) {
        require(numberCupcakes > 0) { "Number of cupcakes must be positive" }
        
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    /**
     * Sets the flavor of cupcakes for this order.
     * Only one flavor can be selected for the entire order.
     * 
     * @param desiredFlavor The flavor to set for the order
     */
    fun setFlavor(desiredFlavor: String) {
        require(desiredFlavor.isNotBlank()) { "Flavor cannot be blank" }
        
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    /**
     * Sets the pickup date for this order and recalculates the price.
     * 
     * @param pickupDate The selected pickup date
     */
    fun setDate(pickupDate: String) {
        require(pickupDate.isNotBlank()) { "Pickup date cannot be blank" }
        
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    /**
     * Resets the order to its initial state with fresh pickup options.
     */
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = generatePickupOptions())
    }

    /**
     * Calculates the total price for the current order.
     * 
     * @param quantity Number of cupcakes (defaults to current state)
     * @param pickupDate Selected pickup date (defaults to current state)
     * @return Formatted price string in local currency
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        var calculatedPrice = quantity * OrderConstants.PRICE_PER_CUPCAKE
        
        // Add surcharge for same-day pickup (first option in the list)
        val pickupOptions = generatePickupOptions()
        if (pickupOptions.isNotEmpty() && pickupOptions[0] == pickupDate) {
            calculatedPrice += OrderConstants.PRICE_FOR_SAME_DAY_PICKUP
        }
        
        return NumberFormat.getCurrencyInstance().format(calculatedPrice)
    }

    /**
     * Generates a list of available pickup dates starting from today.
     * 
     * @return List of formatted date strings for pickup options
     */
    private fun generatePickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat(OrderConstants.DATE_FORMAT_PATTERN, Locale.getDefault())
        val calendar = Calendar.getInstance()
        
        repeat(OrderConstants.PICKUP_OPTIONS_COUNT) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        
        return dateOptions
    }
}