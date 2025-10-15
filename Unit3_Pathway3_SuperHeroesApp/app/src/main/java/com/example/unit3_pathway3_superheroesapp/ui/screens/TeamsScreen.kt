package com.example.unit3_pathway3_superheroesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway3_superheroesapp.R
import com.example.unit3_pathway3_superheroesapp.data.HeroesRepository
import com.example.unit3_pathway3_superheroesapp.model.Hero
import com.example.unit3_pathway3_superheroesapp.ui.components.HeroCard
import com.example.unit3_pathway3_superheroesapp.ui.theme.Unit3_Pathway3_SuperHeroesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamsScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val heroes = HeroesRepository().getHeroes()
    val teamA = heroes.take(3) // 3 nhân vật đầu tiên
    val teamB = heroes.drop(3) // 3 nhân vật cuối
    
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.teams_title),
                        style = MaterialTheme.typography.displayLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        if (isLandscape) {
            // Landscape mode: Side by side layout
            Row(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Team A - Left side
                TeamSection(
                    teamName = stringResource(R.string.team_a),
                    heroes = teamA,
                    isLandscape = true,
                    modifier = Modifier.weight(1f)
                )
                
                // Team B - Right side
                TeamSection(
                    teamName = stringResource(R.string.team_b),
                    heroes = teamB,
                    isLandscape = true,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            // Portrait mode: Vertical layout
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 8.dp,
                    end = 16.dp,
                    bottom = 8.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // Team A Section
                item {
                    TeamSection(
                        teamName = stringResource(R.string.team_a),
                        heroes = teamA,
                        isLandscape = false
                    )
                }
                
                // Team B Section
                item {
                    TeamSection(
                        teamName = stringResource(R.string.team_b),
                        heroes = teamB,
                        isLandscape = false
                    )
                }
            }
        }
    }
}

@Composable
fun TeamSection(
    teamName: String,
    heroes: List<Hero>,
    isLandscape: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        // Team Header
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = if (isLandscape) 8.dp else 16.dp
                    )
            ) {
                Text(
                    text = teamName,
                    style = if (isLandscape) 
                        MaterialTheme.typography.headlineSmall 
                    else 
                        MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Team Members
        if (isLandscape) {
            // In landscape mode, use scrollable column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                heroes.forEach { hero ->
                    HeroCard(
                        hero = hero,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        } else {
            // In portrait mode, regular spacing
            heroes.forEach { hero ->
                HeroCard(
                    hero = hero,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamsScreenPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        TeamsScreen(
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TeamsScreenDarkPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        TeamsScreen(
            onBackClick = {}
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 720,
    heightDp = 360,
    name = "Landscape Preview"
)
@Composable
fun TeamsScreenLandscapePreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        TeamsScreen(
            onBackClick = {}
        )
    }
}

@Preview(
    showBackground = true, 
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES,
    widthDp = 720,
    heightDp = 360,
    name = "Landscape Dark Preview"
)
@Composable
fun TeamsScreenLandscapeDarkPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        TeamsScreen(
            onBackClick = {}
        )
    }
}