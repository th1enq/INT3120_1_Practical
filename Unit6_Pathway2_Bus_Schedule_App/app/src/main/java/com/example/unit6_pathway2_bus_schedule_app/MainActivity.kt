package com.example.unit6_pathway2_bus_schedule_app

import com.example.unit6_pathway2_bus_schedule_app.ui.theme.Unit6_Pathway2_Bus_Schedule_AppTheme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.unit6_pathway2_bus_schedule_app.ui.BusScheduleApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit6_Pathway2_Bus_Schedule_AppTheme {
                BusScheduleApp()
            }
        }
    }
}