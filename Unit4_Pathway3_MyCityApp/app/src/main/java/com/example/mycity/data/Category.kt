package com.example.mycity.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    @StringRes val name: Int,
    @DrawableRes val icon:Int,
    val list: List<Place>
)
