package com.example.unit4_pathway2_traininglunchtray

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unit4_pathway2_traininglunchtray.datasource.DataSource
import com.example.unit4_pathway2_traininglunchtray.ui.AccompanimentMenuScreen
import com.example.unit4_pathway2_traininglunchtray.ui.CheckoutScreen
import com.example.unit4_pathway2_traininglunchtray.ui.EntreeMenuScreen
import com.example.unit4_pathway2_traininglunchtray.ui.OrderViewModel
import com.example.unit4_pathway2_traininglunchtray.ui.SideDishMenuScreen
import com.example.unit4_pathway2_traininglunchtray.ui.StartOrderScreen
import com.example.unit4_pathway2_traininglunchtray.util.Constants

/**
 * Enum class representing different screens in the Lunch Tray app.
 */
enum class LunchTrayScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Entree(title = R.string.choose_entree),
    SideDish(title = R.string.choose_side_dish),
    Accompaniment(title = R.string.choose_accompaniment),
    Checkout(title = R.string.order_checkout)
}

/**
 * Main app composable that sets up navigation and screen structure.
 */
@Composable
fun LunchTrayApp() {
    val navController = rememberNavController()
    val viewModel: OrderViewModel = viewModel()

    Scaffold(
        topBar = {
            LunchTrayAppBar(
                navController = navController
            )
        }
    ) { innerPadding ->
        LunchTrayNavigation(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

/**
 * Top app bar for the Lunch Tray app with navigation support.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LunchTrayAppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LunchTrayScreen.valueOf(
        backStackEntry?.destination?.route ?: LunchTrayScreen.Start.name
    )
    val canNavigateBack = navController.previousBackStackEntry != null

    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

/**
 * Navigation setup for the Lunch Tray app.
 */
@Composable
private fun LunchTrayNavigation(
    navController: NavHostController,
    viewModel: OrderViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = LunchTrayScreen.Start.name,
        modifier = modifier
    ) {
        composable(route = LunchTrayScreen.Start.name) {
            StartOrderScreen(
                onStartOrderButtonClicked = {
                    navController.navigate(LunchTrayScreen.Entree.name)
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = LunchTrayScreen.Entree.name) {
            EntreeMenuScreen(
                options = DataSource.entreeMenuItems,
                onCancelButtonClicked = {
                    handleCancelOrder(viewModel, navController)
                },
                onNextButtonClicked = {
                    navController.navigate(LunchTrayScreen.SideDish.name)
                },
                onSelectionChanged = viewModel::updateEntree,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }

        composable(route = LunchTrayScreen.SideDish.name) {
            SideDishMenuScreen(
                options = DataSource.sideDishMenuItems,
                onCancelButtonClicked = {
                    handleCancelOrder(viewModel, navController)
                },
                onNextButtonClicked = {
                    navController.navigate(LunchTrayScreen.Accompaniment.name)
                },
                onSelectionChanged = viewModel::updateSideDish,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }

        composable(route = LunchTrayScreen.Accompaniment.name) {
            AccompanimentMenuScreen(
                options = DataSource.accompanimentMenuItems,
                onCancelButtonClicked = {
                    handleCancelOrder(viewModel, navController)
                },
                onNextButtonClicked = {
                    navController.navigate(LunchTrayScreen.Checkout.name)
                },
                onSelectionChanged = viewModel::updateAccompaniment,
                modifier = Modifier.verticalScroll(rememberScrollState())
            )
        }

        composable(route = LunchTrayScreen.Checkout.name) {
            CheckoutScreen(
                orderUiState = uiState,
                onCancelButtonClicked = {
                    handleCancelOrder(viewModel, navController)
                },
                onNextButtonClicked = {
                    handleSubmitOrder(viewModel, navController)
                },
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium),
                    )
            )
        }
    }
}

/**
 * Handles order cancellation by resetting the order and navigating to start.
 */
private fun handleCancelOrder(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
}

/**
 * Handles order submission by resetting the order and navigating to start.
 */
private fun handleSubmitOrder(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(LunchTrayScreen.Start.name, inclusive = false)
}