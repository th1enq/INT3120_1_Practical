package com.example.unit4_pathway2_traininglunchtray

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit4_pathway2_traininglunchtray.ui.theme.Unit4_Pathway2_TrainingLunchTrayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit4_Pathway2_TrainingLunchTrayTheme {
                LunchTrayApp()
            }
        }
    }
}