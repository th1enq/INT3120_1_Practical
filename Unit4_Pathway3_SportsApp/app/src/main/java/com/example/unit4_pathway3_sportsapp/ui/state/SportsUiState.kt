package com.example.unit4_pathway3_sportsapp.ui.state

import com.example.unit4_pathway3_sportsapp.data.LocalSportsDataProvider
import com.example.unit4_pathway3_sportsapp.model.Sport

/**
 * Represents the different states of data loading
 */
sealed class LoadingState {
    data object Loading : LoadingState()
    data object Success : LoadingState()
    data class Error(val message: String) : LoadingState()
}

/**
 * Represents the navigation state of the app
 */
data class NavigationState(
    val isShowingListPage: Boolean = true,
    val selectedSportId: Int? = null
)

/**
 * Main UI state for the Sports app
 */
data class SportsUiState(
    val sportsList: List<Sport> = emptyList(),
    val currentSport: Sport = LocalSportsDataProvider.defaultSport,
    val navigationState: NavigationState = NavigationState(),
    val loadingState: LoadingState = LoadingState.Success
) {
    val isShowingListPage: Boolean
        get() = navigationState.isShowingListPage
}