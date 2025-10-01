package com.example.unit4_pathway2_trainingcupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unit4_pathway2_trainingcupcake.data.DataSource
import com.example.unit4_pathway2_trainingcupcake.data.OrderUiState
import com.example.unit4_pathway2_trainingcupcake.ui.OrderSummaryScreen
import com.example.unit4_pathway2_trainingcupcake.ui.OrderViewModel
import com.example.unit4_pathway2_trainingcupcake.ui.SelectOptionScreen
import com.example.unit4_pathway2_trainingcupcake.ui.StartOrderScreen

/**
 * Navigation routes for the cupcake app
 */
object CupcakeRoutes {
    const val START = "start"
    const val FLAVOR = "flavor"
    const val PICKUP = "pickup"
    const val SUMMARY = "summary"
}

/**
 * Enum values that represent the screens in the app
 */
enum class CupcakeScreen(@StringRes val title: Int, val route: String) {
    Start(title = R.string.app_name, route = CupcakeRoutes.START),
    Flavor(title = R.string.choose_flavor, route = CupcakeRoutes.FLAVOR),
    Pickup(title = R.string.choose_pickup_date, route = CupcakeRoutes.PICKUP),
    Summary(title = R.string.order_summary, route = CupcakeRoutes.SUMMARY);
    
    companion object {
        fun fromRoute(route: String?): CupcakeScreen {
            return when (route) {
                CupcakeRoutes.FLAVOR -> Flavor
                CupcakeRoutes.PICKUP -> Pickup
                CupcakeRoutes.SUMMARY -> Summary
                else -> Start
            }
        }
    }
}

/**
 * Composable that displays the top app bar with navigation
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        title = { 
            Text(stringResource(currentScreen.title)) 
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
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
 * Main composable for the Cupcake ordering app.
 * 
 * Handles top-level navigation state and provides the app structure with 
 * top app bar and navigation host.
 * 
 * @param viewModel The OrderViewModel instance for managing order state
 * @param navController The navigation controller for handling screen navigation
 */
@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = CupcakeScreen.fromRoute(backStackEntry?.destination?.route)
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        CupcakeNavHost(
            navController = navController,
            viewModel = viewModel,
            uiState = uiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

/**
 * Navigation host that manages the different screens in the Cupcake app.
 * 
 * Sets up the navigation graph with all available destinations and 
 * handles transitions between screens.
 * 
 * @param navController Controller for handling navigation actions
 * @param viewModel ViewModel for managing order state across screens
 * @param uiState Current UI state containing order information
 * @param modifier Modifier for styling and layout customization
 */
@Composable
private fun CupcakeNavHost(
    navController: NavHostController,
    viewModel: OrderViewModel,
    uiState: OrderUiState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CupcakeRoutes.START,
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        composable(route = CupcakeRoutes.START) {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = { quantity ->
                    handleQuantitySelection(quantity, viewModel, navController)
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(R.dimen.padding_medium))
            )
        }
        
        composable(route = CupcakeRoutes.FLAVOR) {
            FlavorSelectionScreen(
                uiState = uiState,
                onFlavorSelected = { flavor -> viewModel.setFlavor(flavor) },
                onNavigateNext = { navController.navigate(CupcakeRoutes.PICKUP) },
                onCancel = { cancelOrderAndNavigateToStart(viewModel, navController) }
            )
        }
        
        composable(route = CupcakeRoutes.PICKUP) {
            PickupSelectionScreen(
                uiState = uiState,
                onDateSelected = { date -> viewModel.setDate(date) },
                onNavigateNext = { navController.navigate(CupcakeRoutes.SUMMARY) },
                onCancel = { cancelOrderAndNavigateToStart(viewModel, navController) }
            )
        }
        
        composable(route = CupcakeRoutes.SUMMARY) {
            val context = LocalContext.current
            OrderSummaryScreen(
                orderUiState = uiState,
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(viewModel, navController)
                },
                onSendButtonClicked = { subject, summary ->
                    shareOrder(context, subject, summary)
                },
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

/**
 * Handles quantity selection and navigation to flavor screen.
 */
private fun handleQuantitySelection(
    quantity: Int,
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.setQuantity(quantity)
    navController.navigate(CupcakeRoutes.FLAVOR)
}

/**
 * Screen for selecting cupcake flavor with improved parameter separation.
 */
@Composable
private fun FlavorSelectionScreen(
    uiState: OrderUiState,
    onFlavorSelected: (String) -> Unit,
    onNavigateNext: () -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val flavorOptions = remember(context) {
        DataSource.flavors.map { id ->
            context.resources.getString(id)
        }
    }
    
    SelectOptionScreen(
        subtotal = uiState.price,
        onNextButtonClicked = onNavigateNext,
        onCancelButtonClicked = onCancel,
        options = flavorOptions,
        onSelectionChanged = onFlavorSelected,
        modifier = Modifier.fillMaxHeight()
    )
}

/**
 * Screen for selecting pickup date with improved parameter organization.
 */
@Composable
private fun PickupSelectionScreen(
    uiState: OrderUiState,
    onDateSelected: (String) -> Unit,
    onNavigateNext: () -> Unit,
    onCancel: () -> Unit
) {
    SelectOptionScreen(
        subtotal = uiState.price,
        onNextButtonClicked = onNavigateNext,
        onCancelButtonClicked = onCancel,
        options = uiState.pickupOptions,
        onSelectionChanged = onDateSelected,
        modifier = Modifier.fillMaxHeight()
    )
}

/**
 * Resets the order state and navigates back to the start screen.
 * 
 * @param viewModel The order view model to reset
 * @param navController The navigation controller for navigation
 */
private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeRoutes.START, inclusive = false)
}

/**
 * Creates an intent to share order details
 */
private fun shareOrder(context: Context, subject: String, summary: String) {
    // Create an ACTION_SEND implicit intent with order details in the intent extras
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}