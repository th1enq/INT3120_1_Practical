package com.example.juicetracker.data

import android.content.Context

class AppDataContainer(private val context: Context) : AppContainer {
    override val juiceRepository: JuiceRepository by lazy {
        RoomJuiceRepository(AppDatabase.getDatabase(context).juiceDao())
    }
}
