package com.example.unit4_pathway3_replyapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit4_pathway3_replyapp.R
import com.example.unit4_pathway3_replyapp.ui.theme.ReplyTheme

/**
 * Reusable error display component with consistent styling and actions.
 */
@Composable
fun ErrorCard(
    errorMessage: String,
    onRetry: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    isWarning: Boolean = false
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isWarning) {
                MaterialTheme.colorScheme.warningContainer
            } else {
                MaterialTheme.colorScheme.errorContainer
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.email_list_item_inner_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isWarning) Icons.Default.Warning else Icons.Default.Error,
                contentDescription = if (isWarning) "Warning" else "Error",
                tint = if (isWarning) {
                    MaterialTheme.colorScheme.onWarningContainer
                } else {
                    MaterialTheme.colorScheme.onErrorContainer
                },
                modifier = Modifier.padding(end = dimensionResource(R.dimen.email_header_content_padding_horizontal))
            )
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = if (isWarning) "Warning" else "Error",
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isWarning) {
                        MaterialTheme.colorScheme.onWarningContainer
                    } else {
                        MaterialTheme.colorScheme.onErrorContainer
                    }
                )
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isWarning) {
                        MaterialTheme.colorScheme.onWarningContainer
                    } else {
                        MaterialTheme.colorScheme.onErrorContainer
                    }
                )
                
                if (onRetry != null || onDismiss != null) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        onDismiss?.let {
                            TextButton(onClick = it) {
                                Text(
                                    text = stringResource(R.string.dismiss),
                                    color = if (isWarning) {
                                        MaterialTheme.colorScheme.onWarningContainer
                                    } else {
                                        MaterialTheme.colorScheme.onErrorContainer
                                    }
                                )
                            }
                        }
                        onRetry?.let {
                            TextButton(onClick = it) {
                                Text(
                                    text = stringResource(R.string.retry),
                                    color = if (isWarning) {
                                        MaterialTheme.colorScheme.onWarningContainer
                                    } else {
                                        MaterialTheme.colorScheme.onErrorContainer
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Extension of Material 3 ColorScheme to add warning colors
 */
val androidx.compose.material3.ColorScheme.warningContainer: androidx.compose.ui.graphics.Color
    @Composable get() = MaterialTheme.colorScheme.secondaryContainer

val androidx.compose.material3.ColorScheme.onWarningContainer: androidx.compose.ui.graphics.Color
    @Composable get() = MaterialTheme.colorScheme.onSecondaryContainer

@Preview(showBackground = true)
@Composable
fun ErrorCardPreview() {
    ReplyTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.email_list_item_vertical_spacing))
        ) {
            ErrorCard(
                errorMessage = "Failed to load emails. Please check your internet connection.",
                onRetry = { },
                onDismiss = { }
            )
            
            ErrorCard(
                errorMessage = "Some emails might be outdated.",
                isWarning = true,
                onDismiss = { }
            )
        }
    }
}