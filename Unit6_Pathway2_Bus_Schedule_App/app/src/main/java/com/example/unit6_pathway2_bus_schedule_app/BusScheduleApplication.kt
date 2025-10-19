package com.example.unit6_pathway2_bus_schedule_app

import android.app.Application
import com.example.unit6_pathway2_bus_schedule_app.ui.data.AppDatabase

class BusScheduleApplication: Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}