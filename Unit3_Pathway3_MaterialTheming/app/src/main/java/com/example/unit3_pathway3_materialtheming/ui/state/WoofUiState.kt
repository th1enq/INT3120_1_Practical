package com.example.unit3_pathway3_materialtheming.ui.state

import com.example.unit3_pathway3_materialtheming.data.Dog

/**
 * UI state for the Woof screen.
 */
data class WoofUiState(
    val dogs: List<Dog> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    /**
     * Returns true if there are no dogs and no error.
     */
    val isEmpty: Boolean
        get() = dogs.isEmpty() && errorMessage == null && !isLoading
}

/**
 * Sealed class representing different UI events.
 */
sealed class WoofUiEvent {
    object Retry : WoofUiEvent()
    object Refresh : WoofUiEvent()
}