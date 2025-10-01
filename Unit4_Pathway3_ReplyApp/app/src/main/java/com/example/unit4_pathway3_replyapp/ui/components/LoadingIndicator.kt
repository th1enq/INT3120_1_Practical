package com.example.unit4_pathway3_replyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway3_replyapp.R
import com.example.unit4_pathway3_replyapp.ui.theme.ReplyTheme

/**
 * Reusable loading indicator component with optional message.
 */
@Composable
fun LoadingIndicator(
    message: String = "Loading...",
    modifier: Modifier = Modifier,
    fillMaxSize: Boolean = true
) {
    val containerModifier = if (fillMaxSize) {
        modifier.fillMaxSize()
    } else {
        modifier.fillMaxWidth()
    }
    
    Box(
        contentAlignment = Alignment.Center,
        modifier = containerModifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.email_header_content_padding_horizontal))
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(48.dp),
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.email_list_item_inner_padding))
            )
        }
    }
}

/**
 * Compact loading indicator for inline use
 */
@Composable
fun CompactLoadingIndicator(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth().padding(dimensionResource(R.dimen.email_header_content_padding_horizontal))
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 2.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    ReplyTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LoadingIndicator(
                message = "Loading your emails...",
                fillMaxSize = false
            )
            
            CompactLoadingIndicator()
        }
    }
}