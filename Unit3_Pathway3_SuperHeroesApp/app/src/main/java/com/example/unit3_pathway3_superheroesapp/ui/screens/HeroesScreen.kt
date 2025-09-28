package com.example.unit3_pathway3_superheroesapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway3_superheroesapp.R
import com.example.unit3_pathway3_superheroesapp.data.HeroesRepository
import com.example.unit3_pathway3_superheroesapp.model.Hero
import com.example.unit3_pathway3_superheroesapp.ui.components.HeroCard
import com.example.unit3_pathway3_superheroesapp.ui.theme.Unit3_Pathway3_SuperHeroesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroesScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.displayLarge
                    )
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        LazyColumn(
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 8.dp,
                end = 16.dp,
                bottom = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(HeroesRepository().getHeroes()) { hero ->
                HeroCard(
                    hero = hero,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeroesScreenPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        HeroesScreen()
    }
}

@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroesScreenDarkPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        HeroesScreen()
    }
}