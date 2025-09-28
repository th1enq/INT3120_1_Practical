package com.example.unit3_pathway3_superheroesapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit3_pathway3_superheroesapp.R
import com.example.unit3_pathway3_superheroesapp.model.Hero
import com.example.unit3_pathway3_superheroesapp.ui.theme.Unit3_Pathway3_SuperHeroesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroCard(
    hero: Hero,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .sizeIn(minHeight = 72.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = stringResource(hero.nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(hero.descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(hero.imageRes),
                    contentDescription = stringResource(hero.nameRes),
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Preview("Light Theme")
@Preview("Dark Theme", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HeroCardPreview() {
    Unit3_Pathway3_SuperHeroesAppTheme {
        HeroCard(
            hero = Hero(
                nameRes = R.string.hero1_name,
                descriptionRes = R.string.hero1_description,
                imageRes = R.drawable.android_superhero_01
            )
        )
    }
}