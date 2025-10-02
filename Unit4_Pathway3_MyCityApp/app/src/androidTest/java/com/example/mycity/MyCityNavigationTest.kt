package com.example.mycity

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MyCityNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMyCityNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            MyCityApp(navController = navController, windowSize = WindowWidthSizeClass.Compact)
        }
    }

    @Test
    fun myCityNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(MyCityScreen.Start.name)
    }

    @Test
    fun myCityNavHost_verifyBackNavigationNotShownOnStart() {
        val back = "Back"
        composeTestRule.onNodeWithContentDescription(back).assertDoesNotExist()
    }

    @Test
    fun myCityNavHost_clickCategoryCard_navigatesToPlacesList() {
        composeTestRule.onNodeWithStringId(R.string.restaurants_category).performClick()
        navController.assertCurrentRouteName(MyCityScreen.PlacesList.name)
    }

    @Test
    fun myCityNavHost_clickPlaceCard_navigatesToPlaceScreen() {
        navigateToPlaceScreen()
        navController.assertCurrentRouteName(MyCityScreen.Place.name)
    }

    @Test
    fun myCityNavHost_clickBackOnPlacesList_navigatesToStart() {
        navigateToPlacesList()
        performNavigateUp()
        navController.assertCurrentRouteName(MyCityScreen.Start.name)
    }

    @Test
    fun myCityNavHost_clickBackOnPlaceScreen_navigatesToPlacesList() {
        navigateToPlaceScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(MyCityScreen.PlacesList.name)
    }

    private fun navigateToPlacesList() {
        composeTestRule.onNodeWithStringId(R.string.restaurants_category).performClick()
    }

    private fun navigateToPlaceScreen() {
        composeTestRule.onNodeWithStringId(R.string.restaurants_category).performClick()
        composeTestRule.onNodeWithStringId(R.string.kavkaz_title).performClick()
    }

    private fun performNavigateUp() {
        val back = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(back).performClick()
    }
}