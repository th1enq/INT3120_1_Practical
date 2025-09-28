package com.example.unit3_pathway3_superheroesapp.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * A data class to represent the information presented in the hero card
 */
data class Hero(
    @StringRes val nameRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int
)