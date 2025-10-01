package com.example.unit4_pathway3_sportsapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unit4_pathway3_sportsapp.model.Sport
import com.example.unit4_pathway3_sportsapp.navigation.SportsDestination
import com.example.unit4_pathway3_sportsapp.navigation.SportsNavigationEvent
import com.example.unit4_pathway3_sportsapp.navigation.SportsNavigationManager
import com.example.unit4_pathway3_sportsapp.repository.SportsRepository
import com.example.unit4_pathway3_sportsapp.ui.state.LoadingState
import com.example.unit4_pathway3_sportsapp.ui.state.NavigationState
import com.example.unit4_pathway3_sportsapp.ui.state.SportsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model for Sports app
 */
class SportsViewModel @Inject constructor(
    private val sportsRepository: SportsRepository,
    private val navigationManager: SportsNavigationManager = SportsNavigationManager()
) : ViewModel() {

    private val _uiState = MutableStateFlow(SportsUiState())
    val uiState: StateFlow<SportsUiState> = _uiState

    init {
        loadSportsData()
    }

    private fun loadSportsData() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadingState = LoadingState.Loading) }
            
            try {
                val sports = sportsRepository.getSports()
                val defaultSport = sportsRepository.getDefaultSport()
                
                _uiState.update { currentState ->
                    currentState.copy(
                        sportsList = sports,
                        currentSport = defaultSport,
                        loadingState = LoadingState.Success
                    )
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        loadingState = LoadingState.Error(
                            e.message ?: "Unknown error occurred"
                        )
                    )
                }
            }
        }
    }

    fun updateCurrentSport(selectedSport: Sport) {
        handleNavigationEvent(SportsNavigationEvent.SelectSport(selectedSport))
    }

    fun navigateToListPage() {
        handleNavigationEvent(SportsNavigationEvent.NavigateToList)
    }

    fun navigateToDetailPage() {
        handleNavigationEvent(SportsNavigationEvent.NavigateToDetail)
    }

    fun navigateBack() {
        handleNavigationEvent(SportsNavigationEvent.NavigateBack)
    }

    private fun handleNavigationEvent(event: SportsNavigationEvent) {
        val currentDestination = navigationManager.getDestinationFromState(
            _uiState.value.isShowingListPage,
            _uiState.value.navigationState.selectedSportId
        )

        navigationManager.handleNavigationEvent(
            event = event,
            currentDestination = currentDestination,
            onNavigate = { destination -> navigateToDestination(destination) }
        )

        // Handle sport selection for navigation events
        if (event is SportsNavigationEvent.SelectSport) {
            _uiState.update { currentState ->
                currentState.copy(currentSport = event.sport)
            }
        }
    }

    private fun navigateToDestination(destination: SportsDestination) {
        _uiState.update { currentState ->
            val newNavigationState = when (destination) {
                is SportsDestination.SportsList -> {
                    NavigationState(
                        isShowingListPage = true,
                        selectedSportId = currentState.navigationState.selectedSportId
                    )
                }
                is SportsDestination.SportsDetail -> {
                    NavigationState(
                        isShowingListPage = false,
                        selectedSportId = destination.sportId
                    )
                }
            }
            currentState.copy(navigationState = newNavigationState)
        }
    }

    fun searchSports(query: String) {
        viewModelScope.launch {
            try {
                val filteredSports = if (query.isBlank()) {
                    sportsRepository.getSports()
                } else {
                    sportsRepository.searchSports(query)
                }
                
                _uiState.update { currentState ->
                    currentState.copy(sportsList = filteredSports)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        loadingState = LoadingState.Error(
                            e.message ?: "Search failed"
                        )
                    )
                }
            }
        }
    }

    fun filterOlympicSports(olympicOnly: Boolean) {
        viewModelScope.launch {
            try {
                val filteredSports = sportsRepository.filterSports(olympicOnly = olympicOnly)
                _uiState.update { currentState ->
                    currentState.copy(sportsList = filteredSports)
                }
            } catch (e: Exception) {
                _uiState.update { currentState ->
                    currentState.copy(
                        loadingState = LoadingState.Error(
                            e.message ?: "Filter failed"
                        )
                    )
                }
            }
        }
    }

    fun resetFilters() {
        loadSportsData()
    }
}