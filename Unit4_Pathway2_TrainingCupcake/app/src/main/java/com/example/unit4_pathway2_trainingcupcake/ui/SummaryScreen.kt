package com.example.unit4_pathway2_trainingcupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit4_pathway2_trainingcupcake.R
import com.example.unit4_pathway2_trainingcupcake.data.OrderUiState
import com.example.unit4_pathway2_trainingcupcake.ui.component.FormattedPriceLabel
import com.example.unit4_pathway2_trainingcupcake.ui.theme.Unit4_Pathway2_TrainingCupcakeTheme

/**
 * Screen that displays the order summary with details and action buttons.
 * 
 * @param orderUiState Current state of the order containing all details
 * @param onCancelButtonClicked Callback when cancel button is pressed
 * @param onSendButtonClicked Callback when send button is pressed with subject and summary
 * @param modifier Modifier for styling and layout
 */
@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources
    val orderDetails = prepareOrderDetails(orderUiState, resources)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OrderDetailsSection(
            orderDetails = orderDetails,
            totalPrice = orderUiState.price
        )
        
        OrderActionButtons(
            onCancelButtonClicked = onCancelButtonClicked,
            onSendButtonClicked = {
                val newOrder = resources.getString(R.string.new_cupcake_order)
                onSendButtonClicked(newOrder, orderDetails.orderSummary)
            }
        )
    }
}

/**
 * Data class to hold prepared order details for display
 */
private data class OrderDetails(
    val numberOfCupcakes: String,
    val orderSummary: String,
    val summaryItems: List<Pair<String, String>>
)

/**
 * Prepares order details from the UI state for display.
 * 
 * @param orderUiState The current order state
 * @param resources Android resources for string formatting
 * @return OrderDetails containing formatted order information
 */
private fun prepareOrderDetails(
    orderUiState: OrderUiState,
    resources: android.content.res.Resources
): OrderDetails {
    val numberOfCupcakes = resources.getQuantityString(
        R.plurals.cupcakes,
        orderUiState.quantity,
        orderUiState.quantity
    )
    
    val orderSummary = try {
        resources.getString(
            R.string.order_details,
            numberOfCupcakes,
            orderUiState.flavor,
            orderUiState.date,
            orderUiState.quantity
        )
    } catch (e: Exception) {
        "Order: $numberOfCupcakes, ${orderUiState.flavor}, ${orderUiState.date}"
    }
    
    val summaryItems = listOf(
        Pair(resources.getString(R.string.quantity), numberOfCupcakes),
        Pair(resources.getString(R.string.flavor), orderUiState.flavor),
        Pair(resources.getString(R.string.pickup_date), orderUiState.date)
    )
    
    return OrderDetails(numberOfCupcakes, orderSummary, summaryItems)
}

/**
 * Section displaying order details with dividers
 */
@Composable
private fun OrderDetailsSection(
    orderDetails: OrderDetails,
    totalPrice: String
) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        orderDetails.summaryItems.forEach { item ->
            OrderSummaryItem(
                label = item.first,
                value = item.second
            )
        }
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
        
        FormattedPriceLabel(
            subtotal = totalPrice,
            modifier = Modifier.align(Alignment.End)
        )
    }
}

/**
 * Individual order summary item with label and value
 */
@Composable
private fun OrderSummaryItem(
    label: String,
    value: String
) {
    Text(
        text = label.uppercase(),
        style = MaterialTheme.typography.labelMedium
    )
    Text(
        text = value,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge
    )
    Divider(thickness = dimensionResource(R.dimen.thickness_divider))
}

/**
 * Action buttons section with send and cancel buttons.
 * 
 * @param onCancelButtonClicked Callback for cancel button
 * @param onSendButtonClicked Callback for send button
 */
@Composable
private fun OrderActionButtons(
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onSendButtonClicked
        ) {
            Text(
                text = stringResource(R.string.send),
                style = MaterialTheme.typography.bodyLarge
            )
        }
        
        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCancelButtonClicked
        ) {
            Text(
                text = stringResource(R.string.cancel),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview
@Composable
fun OrderSummaryPreview() {
    Unit4_Pathway2_TrainingCupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(0, "Test", "Test", "$300.00"),
            onSendButtonClicked = { subject: String, summary: String -> },
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}