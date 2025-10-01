package com.example.unit4_pathway3_replyapp.ui.theme

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.example.unit4_pathway3_replyapp.R

/**
 * Centralized spacing and dimension values for consistent UI layout.
 * Provides a single source of truth for all spacing-related values in the app.
 */
object ReplySpacing {
    
    /**
     * Get standard padding values from dimension resources
     */
    val extraSmall: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.email_header_content_padding_vertical)
    val small: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.email_list_item_header_subject_spacing)
    val medium: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.email_header_content_padding_horizontal)
    val large: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.email_list_item_inner_padding)
    val extraLarge: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.detail_card_outer_padding_horizontal)
    
    /**
     * Content padding for different layout scenarios
     */
    @Composable
    fun contentPadding(): PaddingValues = PaddingValues(medium)
    
    @Composable
    fun cardPadding(): PaddingValues = PaddingValues(large)
    
    @Composable
    fun listItemPadding(): PaddingValues = PaddingValues(
        horizontal = medium,
        vertical = small
    )
}

/**
 * Centralized size values for UI elements
 */
object ReplySizing {
    
    /**
     * Icon sizes
     */
    val iconSmall: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.email_header_profile_size)
    val iconMedium: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.topbar_profile_image_size)
    val iconLarge: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.profile_image_size)
    
    /**
     * Navigation drawer width
     */
    val drawerWidth: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.drawer_width)
    
    /**
     * Logo size
     */
    val logoSize: Dp @Composable @ReadOnlyComposable get() = dimensionResource(R.dimen.topbar_logo_size)
}