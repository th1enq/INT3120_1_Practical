package com.example.juicetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.juicetracker.ui.JuiceTrackerApp
import com.example.juicetracker.ui.theme.JuiceTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            JuiceTrackerTheme {
                JuiceTrackerApp()
            }
        }
    }
}
