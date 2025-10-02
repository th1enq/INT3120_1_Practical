package com.example.mycity

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.data.Category
import com.example.mycity.data.Datasource
import com.example.mycity.data.Place
import com.example.mycity.ui.MyCityUiState
import com.example.mycity.ui.PickCategoryScreen
import com.example.mycity.ui.PickPlaceScreen
import com.example.mycity.ui.PlaceScreen
import org.junit.Rule
import org.junit.Test

class ScreenTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeUiState = MyCityUiState(
        categories = Datasource.listOfCategories,
        currentCategory = Category(
            name = R.string.restaurants_category,
            icon = R.drawable.restaurant_icon,
            list = emptyList()
        ),
        currentPlace = Place(
            name = R.string.kavkaz_title,
            description = R.string.kavkaz_description,
            address = R.string.kavkaz_address,
            photo = R.drawable.kavkaz
        )
    )

    @Test
    fun pickCategoryScreen_verifyContent() {
        composeTestRule.setContent {
            PickCategoryScreen(viewModel = viewModel(), uiState = fakeUiState, navigateFunction = { })
        }
        Datasource.listOfCategories.forEach {
            composeTestRule.onNodeWithStringId(it.name).assertIsDisplayed()
        }
    }

    @Test
    fun pickPlaceScreen_verifyContent() {
        composeTestRule.setContent {
            PickPlaceScreen(viewModel = viewModel(), uiState = fakeUiState, navigateFunction = {})
        }
        fakeUiState.currentCategory!!.list.forEach{
            composeTestRule.onNodeWithStringId(it.name).assertIsDisplayed()
        }
    }

    @Test
    fun placeScreen_verifyContent(){
        composeTestRule.setContent {
            PlaceScreen(uiState = fakeUiState)
        }

        composeTestRule.onNodeWithStringId(fakeUiState.currentPlace!!.name).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(fakeUiState.currentPlace!!.description).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(fakeUiState.currentPlace!!.address).assertIsDisplayed()

    }

}