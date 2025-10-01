package com.example.unit4_pathway2_trainingcupcake.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Dark color scheme for the Cupcake app
 */
private val DarkColorScheme = darkColorScheme(
    primary = CupcakePrimaryDark,
    onPrimary = CupcakePrimary,
    primaryContainer = CupcakePrimaryContainerDark,
    onPrimaryContainer = CupcakePrimaryContainer,
    
    secondary = CupcakeSecondaryDark,
    onSecondary = CupcakeSecondary,
    secondaryContainer = CupcakeSecondaryContainerDark,
    onSecondaryContainer = CupcakeSecondaryContainer,
    
    tertiary = CupcakeTertiaryDark,
    onTertiary = CupcakeTertiary,
    tertiaryContainer = CupcakeTertiaryContainerDark,
    onTertiaryContainer = CupcakeTertiaryContainer,
    
    surface = CupcakeSurfaceDark,
    onSurface = CupcakeSurface,
    outline = CupcakeOutlineDark
)

/**
 * Light color scheme for the Cupcake app
 */
private val LightColorScheme = lightColorScheme(
    primary = CupcakePrimary,
    onPrimary = CupcakeSurface,
    primaryContainer = CupcakePrimaryContainer,
    onPrimaryContainer = CupcakePrimary,
    
    secondary = CupcakeSecondary,
    onSecondary = CupcakeSurface,
    secondaryContainer = CupcakeSecondaryContainer,
    onSecondaryContainer = CupcakeSecondary,
    
    tertiary = CupcakeTertiary,
    onTertiary = CupcakeSurface,
    tertiaryContainer = CupcakeTertiaryContainer,
    onTertiaryContainer = CupcakeTertiary,
    
    surface = CupcakeSurface,
    onSurface = CupcakeSurfaceDark,
    outline = CupcakeOutline
)

/**
 * Main theme composable for the Cupcake app.
 * 
 * Automatically handles dark/light mode and dynamic colors on Android 12+
 * 
 * @param darkTheme Whether to use dark theme colors
 * @param dynamicColor Whether to use dynamic colors (Android 12+)
 * @param content The content to wrap with theme
 */
@Composable
fun Unit4_Pathway2_TrainingCupcakeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}