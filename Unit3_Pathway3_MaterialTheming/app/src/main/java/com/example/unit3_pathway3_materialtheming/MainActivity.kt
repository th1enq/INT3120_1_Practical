package com.example.unit3_pathway3_materialtheming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit3_pathway3_materialtheming.ui.screens.WoofScreen
import com.example.unit3_pathway3_materialtheming.ui.theme.Unit3_Pathway3_MaterialThemingTheme

/**
 * Main activity for the Woof app, demonstrating Material Design 3 theming
 * and best practices for Jetpack Compose applications.
 */
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofApp()
        }
    }
}

/**
 * Main app composable that sets up the Material Design 3 theme and displays the Woof screen.
 * This composable serves as the entry point for the app's UI hierarchy.
 */
@Composable
fun WoofApp() {
    Unit3_Pathway3_MaterialThemingTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WoofScreen()
        }
    }
}

/**
 * Composable that displays what the UI of the app looks like in light theme.
 */
@Preview(showBackground = true, name = "Light Theme")
@Composable
fun WoofPreview() {
    WoofApp()
}

/**
 * Composable that displays what the UI of the app looks like in dark theme.
 */
@Preview(showBackground = true, name = "Dark Theme")
@Composable
fun WoofDarkThemePreview() {
    Unit3_Pathway3_MaterialThemingTheme(darkTheme = true) {
        WoofApp()
    }
}