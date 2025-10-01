package com.example.mycity.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(percent = 50),
    medium = RoundedCornerShape(
        topEnd = 20.dp, topStart = 40.dp,
        bottomEnd = 20.dp, bottomStart = 40.dp
    ),
    large = RoundedCornerShape(topEnd = 60.dp)
)