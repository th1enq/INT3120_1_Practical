package com.example.unit4_pathway3_sportsapp.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.weight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unit4_pathway3_sportsapp.R
import com.example.unit4_pathway3_sportsapp.data.LocalSportsDataProvider
import com.example.unit4_pathway3_sportsapp.model.Sport
import com.example.unit4_pathway3_sportsapp.ui.theme.Unit4_Pathway3_SportsAppTheme

/**
 * Composable for showing both sports list and detail in a dual-pane layout
 */
@Composable
fun SportsListAndDetail(
    sports: List<Sport>,
    selectedSport: Sport,
    onClick: (Sport) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    Row(
        modifier = modifier
    ) {
        SportsList(
            sports = sports,
            onClick = onClick,
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = dimensionResource(R.dimen.padding_medium))
        )
        SportsDetail(
            selectedSport = selectedSport,
            modifier = Modifier.weight(3f),
            contentPadding = PaddingValues(
                top = contentPadding.calculateTopPadding(),
            ),
            onBackPressed = onBackPressed,
        )
    }
}

@Preview(device = Devices.TABLET)
@Composable
fun SportsListAndDetailsPreview() {
    Unit4_Pathway3_SportsAppTheme {
        SportsListAndDetail(
            sports = LocalSportsDataProvider.getSportsData(),
            selectedSport = LocalSportsDataProvider.getSportsData().getOrElse(0) {
                LocalSportsDataProvider.defaultSport
            },
            onClick = {},
            onBackPressed = {},
        )
    }
}