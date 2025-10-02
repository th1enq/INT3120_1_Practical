package com.example.mycity.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.R
import com.example.mycity.ui.theme.MyCityTheme


@Composable
fun ExpandedStartScreen(
    viewModel: MyCityViewModel,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            shape = MaterialTheme.shapes.medium,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.categories),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.padding_medium),
                        start = dimensionResource(id = R.dimen.padding_large)
                    )
                )
                ExpandedPickCategoryScreen(
                    viewModel = viewModel, uiState = uiState, modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_medium)
                    )
                )
            }

        }
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            shape = MaterialTheme.shapes.large,
            backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                    when (uiState.currentPlace) {
                        null -> StartScreen(modifier = Modifier.fillMaxSize())
                        else -> PlaceScreen(uiState = uiState, modifier = Modifier.fillMaxSize())
                    }


            }
        }


    }
}


@Composable
fun StartScreen(modifier: Modifier = Modifier) {

        Card(
            modifier = modifier,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            shape = MaterialTheme.shapes.medium
        ) {
            Column {
                Image(painter = painterResource(id = R.drawable.yerevan), contentDescription = null)
                Column(Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
                    Text(
                        text = stringResource(id = R.string.yerevan_title),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_small))
                    )
                    Text(
                        text = stringResource(id = R.string.yerevan_description),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify
                    )
                }

            }

        }
    }

@Preview(
    device = Devices.TABLET,
    showBackground = true
)
@Composable
fun ExpandedStartScreenPreview() {
    MyCityTheme {
        ExpandedStartScreen(viewModel = viewModel(), uiState = MyCityUiState())
    }
}
