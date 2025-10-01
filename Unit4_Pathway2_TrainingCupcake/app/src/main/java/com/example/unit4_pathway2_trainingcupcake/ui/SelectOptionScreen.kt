package com.example.unit4_pathway2_trainingcupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit4_pathway2_trainingcupcake.R
import com.example.unit4_pathway2_trainingcupcake.ui.component.FormattedPriceLabel
import com.example.unit4_pathway2_trainingcupcake.ui.theme.Unit4_Pathway2_TrainingCupcakeTheme

/**
 * Screen for selecting options (flavor or pickup date) with radio buttons.
 * 
 * @param subtotal Current order subtotal to display
 * @param options List of available options to choose from
 * @param onSelectionChanged Callback when user selects an option
 * @param onCancelButtonClicked Callback when cancel button is pressed
 * @param onNextButtonClicked Callback when next button is pressed
 * @param modifier Modifier for styling and layout
 */
@Composable
fun SelectOptionScreen(
    subtotal: String,
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        OptionSelectionSection(
            options = options,
            selectedValue = selectedValue,
            onSelectionChanged = { newValue ->
                selectedValue = newValue
                onSelectionChanged(newValue)
            },
            subtotal = subtotal
        )
        
        ActionButtonsSection(
            isNextEnabled = selectedValue.isNotEmpty(),
            onCancelButtonClicked = onCancelButtonClicked,
            onNextButtonClicked = onNextButtonClicked
        )
    }
}

/**
 * Section containing the list of options with radio buttons
 */
@Composable
private fun OptionSelectionSection(
    options: List<String>,
    selectedValue: String,
    onSelectionChanged: (String) -> Unit,
    subtotal: String
) {
    Column(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
    ) {
        options.forEach { option ->
            OptionItem(
                option = option,
                isSelected = selectedValue == option,
                onSelectionChanged = onSelectionChanged
            )
        }
        
        PriceDividerSection(subtotal = subtotal)
    }
}

/**
 * Individual option item with radio button and text
 */
@Composable
private fun OptionItem(
    option: String,
    isSelected: Boolean,
    onSelectionChanged: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .selectable(
                selected = isSelected,
                onClick = { onSelectionChanged(option) }
            )
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = { onSelectionChanged(option) }
        )
        Text(
            text = option,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = dimensionResource(R.dimen.padding_small))
        )
    }
}

/**
 * Section with divider and price label
 */
@Composable
private fun PriceDividerSection(subtotal: String) {
    Divider(
        thickness = dimensionResource(R.dimen.thickness_divider),
        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
    )
    
    FormattedPriceLabel(
        subtotal = subtotal,
        modifier = Modifier.padding(
            top = dimensionResource(R.dimen.padding_medium),
            bottom = dimensionResource(R.dimen.padding_medium)
        )
    )
}

/**
 * Section with cancel and next action buttons
 */
@Composable
private fun ActionButtonsSection(
    isNextEnabled: Boolean,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
        verticalAlignment = Alignment.Bottom
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = onCancelButtonClicked
        ) {
            Text(stringResource(R.string.cancel))
        }
        
        Button(
            modifier = Modifier.weight(1f),
            enabled = isNextEnabled,
            onClick = onNextButtonClicked
        ) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview
@Composable
fun SelectOptionPreview() {
    Unit4_Pathway2_TrainingCupcakeTheme {
        SelectOptionScreen(
            subtotal = "299.99",
            options = listOf("Option 1", "Option 2", "Option 3", "Option 4"),
            modifier = Modifier.fillMaxHeight()
        )
    }
}