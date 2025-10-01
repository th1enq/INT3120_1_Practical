package com.example.unit4_pathway2_trainingcupcake.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway2_trainingcupcake.R
import com.example.unit4_pathway2_trainingcupcake.data.DataSource
import com.example.unit4_pathway2_trainingcupcake.ui.theme.Unit4_Pathway2_TrainingCupcakeTheme

/**
 * Start screen that allows users to select cupcake quantity.
 * 
 * @param quantityOptions List of quantity options with resource ID and value pairs
 * @param onNextButtonClicked Callback when quantity is selected
 * @param modifier Modifier for styling and layout
 */
@Composable
fun StartOrderScreen(
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        WelcomeSection()
        QuantitySelectionSection(
            quantityOptions = quantityOptions,
            onNextButtonClicked = onNextButtonClicked
        )
    }
}

/**
 * Welcome section with image and title
 */
@Composable
private fun WelcomeSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        
        CupcakeImage()
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
        
        Text(
            text = stringResource(R.string.order_cupcakes),
            style = MaterialTheme.typography.headlineSmall
        )
        
        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
    }
}

/**
 * Cupcake image with proper dimensions
 */
@Composable
private fun CupcakeImage() {
    Image(
        painter = painterResource(R.drawable.cupcake),
        contentDescription = stringResource(R.string.cupcake_image_description),
        modifier = Modifier.width(300.dp)
    )
}

/**
 * Section with quantity selection buttons
 */
@Composable
private fun QuantitySelectionSection(
    quantityOptions: List<Pair<Int, Int>>,
    onNextButtonClicked: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            dimensionResource(id = R.dimen.padding_medium)
        )
    ) {
        quantityOptions.forEach { option ->
            SelectQuantityButton(
                labelResourceId = option.first,
                onClick = { onNextButtonClicked(option.second) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Reusable button for selecting cupcake quantity.
 * 
 * @param labelResourceId String resource ID for the button label
 * @param onClick Callback when button is clicked
 * @param modifier Modifier for styling and layout
 */
@Composable
fun SelectQuantityButton(
    @StringRes labelResourceId: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(
            text = stringResource(labelResourceId),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun StartOrderPreview() {
    Unit4_Pathway2_TrainingCupcakeTheme {
        StartOrderScreen(
            quantityOptions = DataSource.quantityOptions,
            onNextButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}