package com.example.unit3_pathway3_materialtheming.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit3_pathway3_materialtheming.R
import com.example.unit3_pathway3_materialtheming.data.Dog
import com.example.unit3_pathway3_materialtheming.data.dogs
import com.example.unit3_pathway3_materialtheming.data.repository.DogRepository
import com.example.unit3_pathway3_materialtheming.data.repository.LocalDogRepository
import com.example.unit3_pathway3_materialtheming.ui.components.DogItem
import com.example.unit3_pathway3_materialtheming.ui.components.EmptyState
import com.example.unit3_pathway3_materialtheming.ui.components.ErrorState
import com.example.unit3_pathway3_materialtheming.ui.components.LoadingIndicator
import com.example.unit3_pathway3_materialtheming.ui.components.WoofTopAppBar
import com.example.unit3_pathway3_materialtheming.ui.state.WoofUiState
import com.example.unit3_pathway3_materialtheming.ui.theme.Unit3_Pathway3_MaterialThemingTheme
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart

/**
 * Composable that displays the main screen with an app bar and a list of dogs.
 * 
 * @param repository repository to fetch dog data from
 * @param modifier modifiers to set to this composable
 */
@Composable
fun WoofScreen(
    repository: DogRepository = LocalDogRepository(),
    modifier: Modifier = Modifier
) {
    var uiState by remember { mutableStateOf(WoofUiState()) }
    
    // Collect dog data with error handling
    LaunchedEffect(repository) {
        repository.getDogs()
            .onStart {
                uiState = uiState.copy(isLoading = true, errorMessage = null)
            }
            .catch { exception ->
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = exception.message ?: "Unknown error occurred"
                )
            }
            .collect { dogs ->
                uiState = uiState.copy(
                    dogs = dogs,
                    isLoading = false,
                    errorMessage = null
                )
            }
    }
    
    WoofScreenContent(
        uiState = uiState,
        onRetry = {
            // Trigger data refresh by recreating the LaunchedEffect
            uiState = uiState.copy(isLoading = true, errorMessage = null)
        },
        modifier = modifier
    )
}

/**
 * Composable that displays the content of the Woof screen.
 * Separated for easier testing and reusability.
 * 
 * @param uiState current UI state
 * @param onRetry callback for retry action
 * @param modifier modifiers to set to this composable
 */
@Composable
internal fun WoofScreenContent(
    uiState: WoofUiState,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            WoofTopAppBar()
        },
        modifier = modifier
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }
            
            uiState.errorMessage != null -> {
                ErrorState(
                    errorMessage = uiState.errorMessage,
                    onRetry = onRetry
                )
            }
            
            uiState.isEmpty -> {
                EmptyState()
            }
            
            else -> {
                DogList(
                    dogs = uiState.dogs,
                    contentPadding = PaddingValues(
                        top = paddingValues.calculateTopPadding(),
                        bottom = paddingValues.calculateBottomPadding() + dimensionResource(R.dimen.padding_small),
                        start = dimensionResource(R.dimen.padding_small),
                        end = dimensionResource(R.dimen.padding_small)
                    )
                )
            }
        }
    }
}

/**
 * Optimized dog list composable with performance enhancements.
 * 
 * @param dogs list of dogs to display
 * @param contentPadding padding for the list content
 * @param modifier modifiers to set to this composable
 */
@Composable
private fun DogList(
    dogs: List<Dog>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    // Remember dogs list to prevent unnecessary recomposition
    val stableDogs = remember(dogs) { dogs }
    
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        items(
            items = stableDogs,
            key = { dog -> dog.createUniqueKey() }
        ) { dog ->
            DogItem(dog = dog)
        }
    }
}

@Preview(showBackground = true, name = "Woof Screen - Light")
@Composable
fun WoofScreenPreview() {
    Unit3_Pathway3_MaterialThemingTheme(darkTheme = false) {
        WoofScreenContent(
            uiState = WoofUiState(dogs = dogs),
            onRetry = { }
        )
    }
}

@Preview(showBackground = true, name = "Woof Screen - Dark")
@Composable
fun WoofScreenDarkThemePreview() {
    Unit3_Pathway3_MaterialThemingTheme(darkTheme = true) {
        WoofScreenContent(
            uiState = WoofUiState(dogs = dogs),
            onRetry = { }
        )
    }
}

@Preview(showBackground = true, name = "Woof Screen - Loading")
@Composable
fun WoofScreenLoadingPreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        WoofScreenContent(
            uiState = WoofUiState(isLoading = true),
            onRetry = { }
        )
    }
}

@Preview(showBackground = true, name = "Woof Screen - Error")
@Composable
fun WoofScreenErrorPreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        WoofScreenContent(
            uiState = WoofUiState(errorMessage = "Network error occurred"),
            onRetry = { }
        )
    }
}

@Preview(showBackground = true, name = "Woof Screen - Empty")
@Composable
fun WoofScreenEmptyPreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        WoofScreenContent(
            uiState = WoofUiState(dogs = emptyList()),
            onRetry = { }
        )
    }
}