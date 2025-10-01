package com.example.unit4_pathway3_replyapp.ui.utils

/**
 * Constants for UI dimensions, animations, and other values used throughout the app.
 * Centralizes magic numbers and provides a single source of truth for UI constants.
 */
object ReplyConstants {
    
    // Animation durations (in milliseconds)
    const val ANIMATION_DURATION_SHORT = 150
    const val ANIMATION_DURATION_MEDIUM = 300
    const val ANIMATION_DURATION_LONG = 500
    
    // Navigation drawer width
    const val NAVIGATION_DRAWER_WIDTH_DP = 240
    
    // Email list configuration
    const val EMAIL_LIST_MAX_LINES = 2
    const val EMAIL_PREVIEW_MAX_CHARACTERS = 100
    
    // Content padding values (in dp)
    const val CONTENT_PADDING_SMALL = 8
    const val CONTENT_PADDING_MEDIUM = 16
    const val CONTENT_PADDING_LARGE = 24
    const val CONTENT_PADDING_EXTRA_LARGE = 32
    
    // Icon sizes (in dp)
    const val ICON_SIZE_SMALL = 16
    const val ICON_SIZE_MEDIUM = 24
    const val ICON_SIZE_LARGE = 32
    const val ICON_SIZE_EXTRA_LARGE = 48
    
    // Profile image sizes (in dp)
    const val PROFILE_IMAGE_SIZE_SMALL = 32
    const val PROFILE_IMAGE_SIZE_MEDIUM = 40
    const val PROFILE_IMAGE_SIZE_LARGE = 56
    
    // Button configuration
    const val BUTTON_MIN_HEIGHT_DP = 48
    const val BUTTON_CORNER_RADIUS_DP = 8
    
    // Card configuration
    const val CARD_CORNER_RADIUS_DP = 12
    const val CARD_ELEVATION_DP = 4
    
    // Content description strings
    const val CONTENT_DESC_PROFILE_IMAGE = "Profile image"
    const val CONTENT_DESC_NAVIGATION_BACK = "Navigate back"
    const val CONTENT_DESC_APP_LOGO = "Reply app logo"
    
    // Test tags
    const val TEST_TAG_NAVIGATION_DRAWER = "navigation_drawer"
    const val TEST_TAG_NAVIGATION_RAIL = "navigation_rail"
    const val TEST_TAG_BOTTOM_NAVIGATION = "bottom_navigation"
    const val TEST_TAG_EMAIL_LIST = "email_list"
    const val TEST_TAG_EMAIL_DETAIL = "email_detail"
}