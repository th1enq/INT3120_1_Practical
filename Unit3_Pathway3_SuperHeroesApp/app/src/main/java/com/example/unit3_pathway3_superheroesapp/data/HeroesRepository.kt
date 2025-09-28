package com.example.unit3_pathway3_superheroesapp.data

import com.example.unit3_pathway3_superheroesapp.R
import com.example.unit3_pathway3_superheroesapp.model.Hero

class HeroesRepository {
    fun getHeroes(): List<Hero> {
        return listOf(
            Hero(
                nameRes = R.string.hero1_name,
                descriptionRes = R.string.hero1_description,
                imageRes = R.drawable.android_superhero_01
            ),
            Hero(
                nameRes = R.string.hero2_name,
                descriptionRes = R.string.hero2_description,
                imageRes = R.drawable.android_superhero_02
            ),
            Hero(
                nameRes = R.string.hero3_name,
                descriptionRes = R.string.hero3_description,
                imageRes = R.drawable.android_superhero_03
            ),
            Hero(
                nameRes = R.string.hero4_name,
                descriptionRes = R.string.hero4_description,
                imageRes = R.drawable.android_superhero_04
            ),
            Hero(
                nameRes = R.string.hero5_name,
                descriptionRes = R.string.hero5_description,
                imageRes = R.drawable.android_superhero_05
            ),
            Hero(
                nameRes = R.string.hero6_name,
                descriptionRes = R.string.hero6_description,
                imageRes = R.drawable.android_superhero_06
            )
        )
    }
}