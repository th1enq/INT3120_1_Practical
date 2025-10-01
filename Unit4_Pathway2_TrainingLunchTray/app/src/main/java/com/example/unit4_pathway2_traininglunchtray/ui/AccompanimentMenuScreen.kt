package com.example.unit4_pathway2_traininglunchtray.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit4_pathway2_traininglunchtray.R
import com.example.unit4_pathway2_traininglunchtray.datasource.DataSource
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem
import com.example.unit4_pathway2_traininglunchtray.model.MenuItem.AccompanimentItem

/**
 * Screen for selecting an accompaniment (like bread or small extras) for the lunch order.
 * Displays available accompaniment options with radio button selection.
 */
@Composable
fun AccompanimentMenuScreen(
    options: List<AccompanimentItem>,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit,
    onSelectionChanged: (AccompanimentItem) -> Unit,
    modifier: Modifier = Modifier
) {
    BaseMenuScreen(
        options = options,
        onCancelButtonClicked = onCancelButtonClicked,
        onNextButtonClicked = onNextButtonClicked,
        onSelectionChanged = { item ->
            if (item is AccompanimentItem) {
                onSelectionChanged(item)
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun AccompanimentMenuPreview() {
    AccompanimentMenuScreen(
        options = DataSource.accompanimentMenuItems,
        onNextButtonClicked = {},
        onCancelButtonClicked = {},
        onSelectionChanged = {},
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    )
}