package com.example.mycity.ui

import androidx.lifecycle.ViewModel
import com.example.mycity.data.Category
import com.example.mycity.data.Datasource
import com.example.mycity.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState

    init {
        initializeUiState()
    }

    private fun initializeUiState() {
        _uiState.value = MyCityUiState(
            categories = Datasource.listOfCategories
        )
    }

    fun updateCurrentCategory(category: Category) {
        _uiState.update {
            it.copy(
                currentCategory = category
            )
        }
    }


    fun updateCurrentPlace(place: Place?) {
        _uiState.update {
            it.copy(
                currentPlace = place
            )
        }
    }

    fun getNextPlace(): Place? {
        return if (_uiState.value.currentPlace != null) {
            getNext(_uiState.value.currentCategory!!.list, _uiState.value.currentPlace)
        } else
            null
    }
    fun getPreviousPlace(): Place? {
        return if (_uiState.value.currentPlace != null) {
            getPrevious(_uiState.value.currentCategory!!.list, _uiState.value.currentPlace)
        } else
            null
    }

    fun getNextCategory(): Category? {
        return if (_uiState.value.currentCategory != null) {
            getNext(Datasource.listOfCategories, _uiState.value.currentCategory)
        } else
            null
    }
    fun getPreviousCategory(): Category? {
        return if (_uiState.value.currentCategory != null) {
            getPrevious(Datasource.listOfCategories, _uiState.value.currentCategory)
        } else
            null
    }

    private fun <T> getNext(list: List<T>, current: T): T {
        val currentIndex = list.indexOf(current)
        return if (currentIndex < list.size - 1) {
            list[currentIndex + 1]
        } else {
            list[0]
        }
    }

    private fun <T> getPrevious(list: List<T>, current: T): T {
        val currentIndex = list.indexOf(current)
        return if (currentIndex > 0) {
            list[currentIndex - 1]
        } else {
            list[list.size - 1]
        }
    }
}