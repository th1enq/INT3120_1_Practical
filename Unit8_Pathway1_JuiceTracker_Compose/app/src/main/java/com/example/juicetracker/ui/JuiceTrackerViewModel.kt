package com.example.juicetracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.juicetracker.data.Juice
import com.example.juicetracker.data.JuiceColor
import com.example.juicetracker.data.JuiceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JuiceTrackerViewModel(private val juiceRepository: JuiceRepository) : ViewModel() {
    private val emptyJuice = Juice(0, "", "", JuiceColor.Red.name, 3)
    private val _currentJuiceStream = MutableStateFlow(emptyJuice)
    val currentJuiceStream: StateFlow<Juice> = _currentJuiceStream
    val juiceListStream: Flow<List<Juice>> = juiceRepository.juiceStream

    fun resetCurrentJuice() = _currentJuiceStream.update { emptyJuice }
    fun updateCurrentJuice(juice: Juice) = _currentJuiceStream.update { juice }

    fun saveJuice() = viewModelScope.launch {
        if (_currentJuiceStream.value.id > 0) {
            juiceRepository.updateJuice(_currentJuiceStream.value)
        } else {
            juiceRepository.addJuice(_currentJuiceStream.value)
        }
    }

    fun deleteJuice(juice: Juice) = viewModelScope.launch {
        juiceRepository.deleteJuice(juice)
    }
}
