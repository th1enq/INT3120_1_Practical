package com.example.dessertrelease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dessertrelease.ui.DessertReleaseApp
import com.example.unit6_pathway3_dessert.ui.theme.Unit6_Pathway3_DessertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit6_Pathway3_DessertTheme {
                DessertReleaseApp()
            }
        }
    }
}