package com.example.unit4_pathway3_sportsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.example.unit4_pathway3_sportsapp.ui.SportsApp
import com.example.unit4_pathway3_sportsapp.ui.theme.Unit4_Pathway3_SportsAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity for Sports app
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        setContent {
            Unit4_Pathway3_SportsAppTheme {
                SportsAppContent(
                    onFinish = { finish() }
                )
            }
        }
    }
}

/**
 * Main content composable that handles window insets and window size calculations
 */
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun SportsAppContent(
    onFinish: () -> Unit
) {
    val layoutDirection = LocalLayoutDirection.current
    
    Surface(
        modifier = Modifier
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection)
            )
    ) {
        // Note: This would normally be handled by LocalContext.current as ComponentActivity
        // but we'll access it through a different method since we're in a Composable
        val activity = androidx.compose.ui.platform.LocalContext.current as ComponentActivity
        val windowSize = calculateWindowSizeClass(activity)
        
        SportsApp(
            windowSize = windowSize.widthSizeClass,
            onBackPressed = onFinish
        )
    }
}