package com.example.unit4_pathway3_sportsapp.navigation

import com.example.unit4_pathway3_sportsapp.model.Sport

/**
 * Navigation events for the Sports app
 */
sealed class SportsNavigationEvent {
    data object NavigateToList : SportsNavigationEvent()
    data object NavigateToDetail : SportsNavigationEvent()
    data class SelectSport(val sport: Sport) : SportsNavigationEvent()
    data object NavigateBack : SportsNavigationEvent()
}

/**
 * Navigation destinations in the Sports app
 */
sealed class SportsDestination {
    data object SportsList : SportsDestination()
    data class SportsDetail(val sportId: Int) : SportsDestination()
}

/**
 * Navigation manager for handling navigation logic
 */
class SportsNavigationManager {
    
    fun handleNavigationEvent(
        event: SportsNavigationEvent,
        currentDestination: SportsDestination,
        onNavigate: (SportsDestination) -> Unit
    ) {
        when (event) {
            is SportsNavigationEvent.NavigateToList -> {
                onNavigate(SportsDestination.SportsList)
            }
            is SportsNavigationEvent.NavigateToDetail -> {
                // Navigate to detail with current sport, or default if none selected
                when (currentDestination) {
                    is SportsDestination.SportsDetail -> {
                        // Already on detail page, no action needed
                    }
                    else -> {
                        // Would need sport ID to navigate to detail
                        // This should be handled by SelectSport event instead
                    }
                }
            }
            is SportsNavigationEvent.SelectSport -> {
                onNavigate(SportsDestination.SportsDetail(event.sport.id))
            }
            is SportsNavigationEvent.NavigateBack -> {
                when (currentDestination) {
                    is SportsDestination.SportsDetail -> {
                        onNavigate(SportsDestination.SportsList)
                    }
                    else -> {
                        // Already on list, no back navigation needed
                    }
                }
            }
        }
    }
    
    fun getDestinationFromState(
        isShowingListPage: Boolean,
        selectedSportId: Int?
    ): SportsDestination {
        return if (isShowingListPage) {
            SportsDestination.SportsList
        } else {
            SportsDestination.SportsDetail(selectedSportId ?: 1)
        }
    }
    
    fun shouldShowBackButton(
        destination: SportsDestination,
        isExpandedScreen: Boolean
    ): Boolean {
        return when (destination) {
            is SportsDestination.SportsDetail -> !isExpandedScreen
            else -> false
        }
    }
}