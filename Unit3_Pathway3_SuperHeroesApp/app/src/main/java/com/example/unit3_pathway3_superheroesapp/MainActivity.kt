package com.example.unit3_pathway3_superheroesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.unit3_pathway3_superheroesapp.ui.screens.HeroesScreen
import com.example.unit3_pathway3_superheroesapp.ui.screens.TeamsScreen
import com.example.unit3_pathway3_superheroesapp.ui.theme.Unit3_Pathway3_SuperHeroesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Unit3_Pathway3_SuperHeroesAppTheme {
                SuperheroApp()
            }
        }
    }
}

@Composable
fun SuperheroApp() {
    var currentScreen by rememberSaveable { mutableStateOf(SuperheroScreen.Heroes) }
    
    when (currentScreen) {
        SuperheroScreen.Heroes -> {
            HeroesScreen(
                onNavigateToTeams = {
                    currentScreen = SuperheroScreen.Teams
                },
                modifier = Modifier.fillMaxSize()
            )
        }
        SuperheroScreen.Teams -> {
            TeamsScreen(
                onBackClick = {
                    currentScreen = SuperheroScreen.Heroes
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

enum class SuperheroScreen {
    Heroes,
    Teams
}