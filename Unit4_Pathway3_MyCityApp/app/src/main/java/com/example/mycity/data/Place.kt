package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place(
    @StringRes val name: Int,
    @StringRes val description: Int,
    @DrawableRes val photo: Int,
    @StringRes val address: Int
)
