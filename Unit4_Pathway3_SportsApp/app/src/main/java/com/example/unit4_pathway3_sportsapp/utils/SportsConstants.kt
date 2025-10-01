package com.example.unit4_pathway3_sportsapp.utils

/**
 * Constants used throughout the Sports app
 */
object SportsConstants {
    // Animation constants
    const val DEFAULT_ANIMATION_DURATION = 300
    const val LOADING_TIMEOUT_MS = 5000L
    
    // UI constants
    const val GRADIENT_START_OFFSET = 0f
    const val GRADIENT_END_OFFSET = 400f
    const val MAX_SUBTITLE_LINES = 3
    
    // Default values
    const val DEFAULT_PLAYER_COUNT = 1
    const val MIN_PLAYER_COUNT = 1
    const val MAX_PLAYER_COUNT = 50
    
    // Navigation
    const val NAVIGATION_DEBOUNCE_MS = 500L
}

/**
 * Content type for determining layout based on screen size
 */
enum class SportsContentType {
    ListOnly, 
    ListAndDetail
}