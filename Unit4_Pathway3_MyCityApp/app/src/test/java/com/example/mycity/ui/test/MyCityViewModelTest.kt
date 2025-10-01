package com.example.mycity.ui.test

import com.example.mycity.data.Datasource
import com.example.mycity.ui.MyCityUiState
import com.example.mycity.ui.MyCityViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class MyCityViewModelTest {
    private val viewModel = MyCityViewModel()

    @Test
    fun myCityViewModel_CategoryPicked_CurrentCategoryUpdated(){
        val newCategory=Datasource.listOfCategories[2]
        viewModel.updateCurrentCategory(newCategory)
        val currentUiState: MyCityUiState = viewModel.uiState.value
        assertEquals(newCategory, currentUiState.currentCategory)
    }

    @Test
    fun myCityViewModel_PlacePicked_CurrentCategoryUpdated(){
        val newPlace=Datasource.listOfCategories[0].list[1]
        viewModel.updateCurrentPlace(newPlace)
        val currentUiState: MyCityUiState = viewModel.uiState.value
        assertEquals(newPlace, currentUiState.currentPlace)
    }
}