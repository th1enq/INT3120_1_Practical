package com.example.unit4_pathway2_traininglunchtray.model

/**
 * UI state for the lunch order, containing all selected items and calculated totals.
 */
data class OrderUiState(
    val entree: MenuItem.EntreeItem? = null,
    val sideDish: MenuItem.SideDishItem? = null,
    val accompaniment: MenuItem.AccompanimentItem? = null,
    val itemTotalPrice: Double = 0.0,
    val orderTax: Double = 0.0,
    val orderTotalPrice: Double = 0.0
) {
    /**
     * Checks if the order has at least one item selected.
     */
    val hasItems: Boolean
        get() = entree != null || sideDish != null || accompaniment != null
    
    /**
     * Checks if the order is complete (has entree, side dish, and accompaniment).
     */
    val isComplete: Boolean
        get() = entree != null && sideDish != null && accompaniment != null
    
    /**
     * Gets all selected items as a list.
     */
    val selectedItems: List<MenuItem>
        get() = listOfNotNull(entree, sideDish, accompaniment)
    
    /**
     * Gets the count of selected items.
     */
    val itemCount: Int
        get() = selectedItems.size
}