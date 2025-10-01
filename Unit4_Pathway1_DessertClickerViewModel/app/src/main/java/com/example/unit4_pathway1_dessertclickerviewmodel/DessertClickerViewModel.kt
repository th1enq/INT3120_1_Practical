package com.example.unit4_pathway1_dessertclickerviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unit4_pathway1_dessertclickerviewmodel.data.Datasource
import com.example.unit4_pathway1_dessertclickerviewmodel.model.Dessert

class DessertClickerViewModel : ViewModel() {
    
    // Mutable state for revenue
    var revenue by mutableIntStateOf(0)
        private set
    
    // Mutable state for desserts sold
    var dessertsSold by mutableIntStateOf(0)
        private set
    
    // List of desserts from datasource
    val desserts = Datasource.dessertList
    
    // Current dessert to show based on desserts sold
    val currentDessert: Dessert
        get() = determineDessertToShow(desserts, dessertsSold)
    
    /**
     * Handle dessert click - updates revenue and desserts sold
     */
    fun onDessertClicked() {
        revenue += currentDessert.price
        dessertsSold++
    }
    
    /**
     * Determine which dessert to show based on the number of desserts sold
     */
    private fun determineDessertToShow(
        desserts: List<Dessert>,
        dessertsSold: Int
    ): Dessert {
        return desserts.lastOrNull { dessertsSold >= it.startProductionAmount }
            ?: desserts.first()
    }
}