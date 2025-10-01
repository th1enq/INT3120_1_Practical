package com.example.unit4_pathway2_traininglunchtray.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.semantics.Role
import com.example.unit4_pathway2_traininglunchtray.R
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem

/**
 * Base menu screen composable that provides common layout and functionality
 * for menu selection screens.
 */
@Composable
fun BaseMenuScreen(
    options: List<MenuItem>,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onSelectionChanged: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {
        MenuItemsList(
            options = options,
            selectedItemName = selectedItemName,
            onSelectionChanged = { item ->
                selectedItemName = item.name
                onSelectionChanged(item)
            }
        )

        MenuActionButtons(
            hasSelection = selectedItemName.isNotEmpty(),
            onCancelButtonClicked = onCancelButtonClicked,
            onNextButtonClicked = onNextButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

/**
 * Displays the list of menu items with radio button selection.
 */
@Composable
private fun MenuItemsList(
    options: List<MenuItem>,
    selectedItemName: String,
    onSelectionChanged: (MenuItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        options.forEach { item ->
            MenuItemRow(
                item = item,
                isSelected = selectedItemName == item.name,
                onItemSelected = { onSelectionChanged(item) },
                modifier = Modifier
                    .selectable(
                        selected = selectedItemName == item.name,
                        onClick = { onSelectionChanged(item) },
                        role = Role.RadioButton
                    )
                    .padding(
                        horizontal = dimensionResource(R.dimen.padding_medium)
                    )
            )
        }
    }
}

/**
 * Individual menu item row with radio button and item details.
 */
@Composable
private fun MenuItemRow(
    item: MenuItem,
    isSelected: Boolean,
    onItemSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onItemSelected
        )
        
        MenuItemDetails(
            item = item,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Displays the details of a menu item (name, description, price).
 */
@Composable
private fun MenuItemDetails(
    item: MenuItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
    ) {
        Text(
            text = item.name,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = item.getFormattedPrice(),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Divider(
            thickness = dimensionResource(R.dimen.thickness_divider),
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
    }
}

/**
 * Action buttons for cancel and next navigation.
 */
@Composable
private fun MenuActionButtons(
    hasSelection: Boolean,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ) {
        OutlinedButton(
            onClick = onCancelButtonClicked,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.cancel).uppercase())
        }
        
        Button(
            onClick = onNextButtonClicked,
            enabled = hasSelection,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.next).uppercase())
        }
    }
}