package com.example.unit3_pathway3_superheroesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.example.unit3_pathway3_superheroesapp.ui.screens.HeroesScreen
import com.example.unit3_pathway3_superheroesapp.ui.theme.Unit3_Pathway3_SuperHeroesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3_Pathway3_SuperHeroesAppTheme {
                HeroesScreen(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}