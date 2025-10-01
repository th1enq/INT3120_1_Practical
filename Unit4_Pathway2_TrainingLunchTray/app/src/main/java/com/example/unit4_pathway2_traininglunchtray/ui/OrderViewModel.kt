package com.example.unit4_pathway2_traininglunchtray.ui

import androidx.lifecycle.ViewModel
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.AccompanimentItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.EntreeItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.SideDishItem
import com.example.unit4_pathway2_traininglunchtray.model.OrderUiState
import com.example.unit4_pathway2_traininglunchtray.util.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat

/**
 * ViewModel for managing lunch order state and calculations.
 */
class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    /**
     * Updates the selected entree and recalculates totals.
     */
    fun updateEntree(entree: EntreeItem) {
        val previousEntree = _uiState.value.entree
        updateItem(entree, previousEntree)
    }

    /**
     * Updates the selected side dish and recalculates totals.
     */
    fun updateSideDish(sideDish: SideDishItem) {
        val previousSideDish = _uiState.value.sideDish
        updateItem(sideDish, previousSideDish)
    }

    /**
     * Updates the selected accompaniment and recalculates totals.
     */
    fun updateAccompaniment(accompaniment: AccompanimentItem) {
        val previousAccompaniment = _uiState.value.accompaniment
        updateItem(accompaniment, previousAccompaniment)
    }

    /**
     * Resets the order to initial state.
     */
    fun resetOrder() {
        _uiState.value = OrderUiState()
    }

    /**
     * Updates a menu item in the order and recalculates totals.
     */
    private fun updateItem(newItem: MenuItem, previousItem: MenuItem?) {
        _uiState.update { currentState ->
            val previousItemPrice = previousItem?.price ?: 0.0
            val itemTotalPrice = currentState.itemTotalPrice - previousItemPrice + newItem.price
            val tax = calculateTax(itemTotalPrice)
            
            currentState.copy(
                itemTotalPrice = itemTotalPrice,
                orderTax = tax,
                orderTotalPrice = itemTotalPrice + tax,
                entree = if (newItem is EntreeItem) newItem else currentState.entree,
                sideDish = if (newItem is SideDishItem) newItem else currentState.sideDish,
                accompaniment = if (newItem is AccompanimentItem) newItem else currentState.accompaniment
            )
        }
    }

    /**
     * Calculates tax for the given subtotal.
     */
    private fun calculateTax(subtotal: Double): Double {
        return subtotal * Constants.TAX_RATE
    }
}

/**
 * Extension function to format Double as currency string.
 */
fun Double.formatPrice(): String {
    return NumberFormat.getCurrencyInstance().format(this)
}