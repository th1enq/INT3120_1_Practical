/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sports.ui

import androidx.lifecycle.ViewModel
import com.example.sports.data.LocalSportsDataProvider
import com.example.sports.model.Sport
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Repository interface for accessing sports data.
 * Provides a layer of abstraction for data operations.
 */
interface SportsRepository {
    fun getSportsData(): List<Sport>
    fun getDefaultSport(): Sport
}

/**
 * Default implementation of SportsRepository using LocalSportsDataProvider.
 */
class DefaultSportsRepository : SportsRepository {
    override fun getSportsData(): List<Sport> = LocalSportsDataProvider.getSportsData()
    override fun getDefaultSport(): Sport = LocalSportsDataProvider.defaultSport
}

/**
 * Represents the different navigation states of the app.
 */
enum class NavigationState {
    LIST, DETAIL
}

/**
 * Represents the UI state for the Sports app.
 *
 * @param sportsList The list of available sports
 * @param currentSport The currently selected sport
 * @param navigationState The current navigation state (list or detail view)
 * @param isLoading Whether data is currently being loaded
 * @param errorMessage Optional error message to display to the user
 */
data class SportsUiState(
    val sportsList: List<Sport> = emptyList(),
    val currentSport: Sport? = null,
    val navigationState: NavigationState = NavigationState.LIST,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) {
    /**
     * Convenience property to check if currently showing the list page.
     * Maintained for backward compatibility with existing UI code.
     */
    val isShowingListPage: Boolean
        get() = navigationState == NavigationState.LIST
}

/**
 * ViewModel for the Sports app that manages UI state and handles user interactions.
 * Follows MVVM architecture principles with proper separation of concerns.
 *
 * @param repository The repository for accessing sports data
 */
class SportsViewModel(
    private val repository: SportsRepository = DefaultSportsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(createInitialState())
    
    /**
     * Immutable StateFlow exposing the current UI state.
     * UI components can observe this to react to state changes.
     */
    val uiState: StateFlow<SportsUiState> = _uiState.asStateFlow()

    /**
     * Creates the initial state for the app.
     * Handles the case where sports list might be empty.
     */
    private fun createInitialState(): SportsUiState {
        return try {
            val sportsList = repository.getSportsData()
            val defaultSport = if (sportsList.isNotEmpty()) {
                sportsList.first()
            } else {
                repository.getDefaultSport()
            }
            
            SportsUiState(
                sportsList = sportsList,
                currentSport = defaultSport,
                navigationState = NavigationState.LIST,
                isLoading = false
            )
        } catch (e: Exception) {
            SportsUiState(
                sportsList = emptyList(),
                currentSport = repository.getDefaultSport(),
                navigationState = NavigationState.LIST,
                isLoading = false,
                errorMessage = "Failed to load sports data: ${e.message}"
            )
        }
    }

    /**
     * Updates the currently selected sport and automatically navigates to detail view.
     * Validates that the sport exists in the current sports list.
     *
     * @param selectedSport The sport to select
     */
    fun selectSport(selectedSport: Sport) {
        val currentState = _uiState.value
        
        // Validate that the sport exists in the current list
        if (selectedSport in currentState.sportsList || currentState.sportsList.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    currentSport = selectedSport,
                    navigationState = NavigationState.DETAIL,
                    errorMessage = null
                )
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "Selected sport is not available in the current list"
                )
            }
        }
    }

    /**
     * Updates the currently selected sport without changing navigation state.
     * Useful for tablet layouts where both list and detail are visible.
     *
     * @param selectedSport The sport to select
     */
    fun updateCurrentSport(selectedSport: Sport) {
        val currentState = _uiState.value
        
        if (selectedSport in currentState.sportsList || currentState.sportsList.isEmpty()) {
            _uiState.update { state ->
                state.copy(
                    currentSport = selectedSport,
                    errorMessage = null
                )
            }
        }
    }

    /**
     * Navigates to the list view.
     * Clears any existing error messages.
     */
    fun navigateToListPage() {
        _uiState.update { state ->
            state.copy(
                navigationState = NavigationState.LIST,
                errorMessage = null
            )
        }
    }

    /**
     * Navigates to the detail view.
     * Only navigates if a sport is currently selected.
     */
    fun navigateToDetailPage() {
        val currentState = _uiState.value
        if (currentState.currentSport != null) {
            _uiState.update { state ->
                state.copy(
                    navigationState = NavigationState.DETAIL,
                    errorMessage = null
                )
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    errorMessage = "No sport selected for detail view"
                )
            }
        }
    }

    /**
     * Clears the current error message.
     */
    fun clearError() {
        _uiState.update { state ->
            state.copy(errorMessage = null)
        }
    }

    /**
     * Refreshes the sports data from the repository.
     * Useful for pull-to-refresh scenarios.
     */
    fun refreshSportsData() {
        _uiState.update { state ->
            state.copy(isLoading = true, errorMessage = null)
        }
        
        try {
            val sportsList = repository.getSportsData()
            val currentSport = _uiState.value.currentSport
            
            // Check if current sport is still available after refresh
            val updatedCurrentSport = when {
                currentSport != null && currentSport in sportsList -> currentSport
                sportsList.isNotEmpty() -> sportsList.first()
                else -> repository.getDefaultSport()
            }
            
            _uiState.update { state ->
                state.copy(
                    sportsList = sportsList,
                    currentSport = updatedCurrentSport,
                    isLoading = false,
                    errorMessage = null
                )
            }
        } catch (e: Exception) {
            _uiState.update { state ->
                state.copy(
                    isLoading = false,
                    errorMessage = "Failed to refresh sports data: ${e.message}"
                )
            }
        }
    }
}
