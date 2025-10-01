package com.example.unit4_pathway2_trainingcupcake.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway2_trainingcupcake.R

/**
 * Reusable composable that displays a formatted price label.
 * 
 * @param subtotal The price to display
 * @param modifier Modifier for styling and layout
 */
@Composable
fun FormattedPriceLabel(
    subtotal: String, 
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.subtotal_price, subtotal),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Reusable action button row with cancel and next/confirm buttons.
 * 
 * @param onCancelClick Callback when cancel button is clicked
 * @param onConfirmClick Callback when confirm button is clicked
 * @param confirmText Text for the confirm button
 * @param isConfirmEnabled Whether the confirm button should be enabled
 * @param modifier Modifier for styling and layout
 */
@Composable
fun ActionButtonRow(
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    confirmText: String = stringResource(R.string.next),
    isConfirmEnabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        Button(
            onClick = onConfirmClick,
            enabled = isConfirmEnabled,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = confirmText,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/**
 * Reusable section header text composable.
 * 
 * @param text The header text to display
 * @param modifier Modifier for styling and layout
 */
@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Medium,
        modifier = modifier
    )
}