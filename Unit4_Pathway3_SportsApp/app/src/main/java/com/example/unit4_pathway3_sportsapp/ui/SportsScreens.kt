package com.example.unit4_pathway3_sportsapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.unit4_pathway3_sportsapp.R
import com.example.unit4_pathway3_sportsapp.data.LocalSportsDataProvider
import com.example.unit4_pathway3_sportsapp.ui.components.EmptyStateComponent
import com.example.unit4_pathway3_sportsapp.ui.components.LoadingStateComponent
import com.example.unit4_pathway3_sportsapp.ui.components.SportsAppBar
import com.example.unit4_pathway3_sportsapp.ui.components.SportsDetail
import com.example.unit4_pathway3_sportsapp.ui.components.SportsList
import com.example.unit4_pathway3_sportsapp.ui.components.SportsListAndDetail
import com.example.unit4_pathway3_sportsapp.ui.state.LoadingState
import com.example.unit4_pathway3_sportsapp.ui.theme.Unit4_Pathway3_SportsAppTheme
import com.example.unit4_pathway3_sportsapp.utils.SportsContentType

/**
 * Main composable that serves as container
 * which displays content according to [uiState] and [windowSize]
 */
@Composable
fun SportsApp(
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
    viewModel: SportsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium -> SportsContentType.ListOnly

        WindowWidthSizeClass.Expanded -> SportsContentType.ListAndDetail
        else -> SportsContentType.ListOnly
    }

    Scaffold(
        topBar = {
            SportsAppBar(
                isShowingListPage = uiState.isShowingListPage,
                onBackButtonClick = { viewModel.navigateToListPage() },
                windowSize = windowSize
            )
        }
    ) { innerPadding ->
        when (uiState.loadingState) {
            is LoadingState.Loading -> {
                LoadingStateComponent(
                    loadingState = uiState.loadingState,
                    onRetry = { viewModel.resetFilters() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is LoadingState.Error -> {
                LoadingStateComponent(
                    loadingState = uiState.loadingState,
                    onRetry = { viewModel.resetFilters() },
                    modifier = Modifier.padding(innerPadding)
                )
            }
            is LoadingState.Success -> {
                if (uiState.sportsList.isEmpty()) {
                    EmptyStateComponent(
                        message = stringResource(R.string.no_sports_found),
                        modifier = Modifier.padding(innerPadding)
                    )
                } else {
                    SportsContent(
                        uiState = uiState,
                        contentType = contentType,
                        viewModel = viewModel,
                        onBackPressed = onBackPressed,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

@Composable
private fun SportsContent(
    uiState: com.example.unit4_pathway3_sportsapp.ui.state.SportsUiState,
    contentType: SportsContentType,
    viewModel: SportsViewModel,
    onBackPressed: () -> Unit,
    innerPadding: androidx.compose.foundation.layout.PaddingValues
) {
    when (contentType) {
        SportsContentType.ListAndDetail -> {
            SportsListAndDetail(
                sports = uiState.sportsList,
                selectedSport = uiState.currentSport,
                onClick = { viewModel.updateCurrentSport(it) },
                onBackPressed = onBackPressed,
                contentPadding = innerPadding,
                modifier = Modifier.fillMaxWidth()
            )
        }
        SportsContentType.ListOnly -> {
            if (uiState.isShowingListPage) {
                SportsList(
                    sports = uiState.sportsList,
                    onClick = { sport ->
                        viewModel.updateCurrentSport(sport)
                        viewModel.navigateToDetailPage()
                    },
                    modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.padding_medium)),
                    contentPadding = innerPadding,
                )
            } else {
                SportsDetail(
                    selectedSport = uiState.currentSport,
                    contentPadding = innerPadding,
                    onBackPressed = { viewModel.navigateToListPage() }
                )
            }
        }
    }
}

@Preview
@Composable
fun SportsListPreview() {
    Unit4_Pathway3_SportsAppTheme {
        Surface {
            SportsList(
                sports = LocalSportsDataProvider.getSportsData(),
                onClick = {},
            )
        }
    }
}