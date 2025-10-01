package com.example.unit4_pathway2_traininglunchtray.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit4_pathway2_traininglunchtray.R
import com.example.unit4_pathway2_traininglunchtray.datasource.DataSource
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem
import com.example.unit4_pathway2_traininglunchtray.model.OrderUiState

/**
 * Checkout screen displaying order summary and total calculations.
 */
@Composable
fun CheckoutScreen(
    orderUiState: OrderUiState,
    onNextButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        OrderSummaryHeader()
        
        OrderItemsSection(orderUiState = orderUiState)
        
        OrderTotalsSection(orderUiState = orderUiState)
        
        CheckoutActions(
            onCancelButtonClicked = onCancelButtonClicked,
            onSubmitButtonClicked = onNextButtonClicked
        )
    }
}

/**
 * Header for the order summary section.
 */
@Composable
private fun OrderSummaryHeader(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(R.string.order_summary),
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

/**
 * Section displaying all selected menu items.
 */
@Composable
private fun OrderItemsSection(
    orderUiState: OrderUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        orderUiState.selectedItems.forEach { item ->
            ItemSummaryRow(
                item = item,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Individual item summary row showing name and price.
 */
@Composable
private fun ItemSummaryRow(
    item: MenuItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = item.getFormattedPrice(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

/**
 * Section showing subtotal, tax, and total calculations.
 */
@Composable
private fun OrderTotalsSection(
    orderUiState: OrderUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Divider(
            thickness = dimensionResource(R.dimen.thickness_divider),
            modifier = Modifier.padding(vertical = dimensionResource(R.dimen.padding_small))
        )

        OrderCalculationRow(
            labelRes = R.string.subtotal,
            amount = orderUiState.itemTotalPrice.formatPrice(),
            modifier = Modifier.fillMaxWidth()
        )

        OrderCalculationRow(
            labelRes = R.string.tax,
            amount = orderUiState.orderTax.formatPrice(),
            modifier = Modifier.fillMaxWidth()
        )

        TotalRow(
            total = orderUiState.orderTotalPrice.formatPrice(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

/**
 * Row displaying a calculation line (subtotal or tax).
 */
@Composable
private fun OrderCalculationRow(
    @StringRes labelRes: Int,
    amount: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(labelRes),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = amount,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/**
 * Row displaying the final total amount.
 */
@Composable
private fun TotalRow(
    total: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.total, total),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Action buttons for cancel and submit order.
 */
@Composable
private fun CheckoutActions(
    onCancelButtonClicked: () -> Unit,
    onSubmitButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedButton(
            onClick = onCancelButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.cancel).uppercase())
        }
        
        Button(
            onClick = onSubmitButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.submit).uppercase())
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CheckoutScreenPreview() {
    CheckoutScreen(
        orderUiState = OrderUiState(
            entree = DataSource.entreeMenuItems[0],
            sideDish = DataSource.sideDishMenuItems[0],
            accompaniment = DataSource.accompanimentMenuItems[0],
            itemTotalPrice = 15.00,
            orderTax = 1.00,
            orderTotalPrice = 16.00
        ),
        onNextButtonClicked = {},
        onCancelButtonClicked = {},
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    )
}