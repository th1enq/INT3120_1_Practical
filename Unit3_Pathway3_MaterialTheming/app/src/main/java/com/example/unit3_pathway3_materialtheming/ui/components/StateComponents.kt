package com.example.unit3_pathway3_materialtheming.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit3_pathway3_materialtheming.R
import com.example.unit3_pathway3_materialtheming.ui.theme.Unit3_Pathway3_MaterialThemingTheme

/**
 * Loading indicator component for the Woof app.
 */
@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(dimensionResource(R.dimen.loading_indicator_size)),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Error state component with retry functionality.
 */
@Composable
fun ErrorState(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.error_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.retry_button))
        }
    }
}

/**
 * Empty state component when no dogs are available.
 */
@Composable
fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.empty_dogs_title),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        
        Text(
            text = stringResource(R.string.empty_dogs_message),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        LoadingIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorStatePreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        ErrorState(
            errorMessage = "Failed to load dogs. Please check your internet connection.",
            onRetry = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    Unit3_Pathway3_MaterialThemingTheme {
        EmptyState()
    }
}