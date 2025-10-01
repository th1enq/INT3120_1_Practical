package com.example.mycity.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycity.R
import com.example.mycity.data.Category
import com.example.mycity.ui.theme.MyCityTheme
import com.example.mycity.ui.theme.Shapes

@Composable
fun ExpandedCategoryCard(
    viewModel: MyCityViewModel,
    uiState: MyCityUiState,
    category: Category,
    modifier: Modifier = Modifier,
    colors: CardColors = CardDefaults.cardColors()
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val shouldBeExpanded = uiState.currentCategory == category && expanded
    Card(
        colors = if (shouldBeExpanded) colors else CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        shape = Shapes.medium,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        modifier = modifier.animateContentSize(
            spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
    ) {
        Row(
            modifier = modifier.padding(
                start = dimensionResource(id = R.dimen.padding_medium),
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = category.icon),
                contentDescription = null,
                modifier = Modifier.padding(end = dimensionResource(id = R.dimen.padding_medium))
            )
            Column {
                Text(
                    text = stringResource(id = category.name),
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Number of ${stringResource(id = category.name).lowercase()}: " + category.list.size.toString(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {
                viewModel.updateCurrentCategory(category)
                expanded = !expanded
                if (shouldBeExpanded) viewModel.updateCurrentPlace(null)
            }, modifier = Modifier.size(60.dp)) {
                Icon(
                    imageVector = if (shouldBeExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = dimensionResource(id = R.dimen.padding_small),
                            end = dimensionResource(id = R.dimen.padding_medium)
                        )
                )

            }
        }
        if (shouldBeExpanded) {
            ExpandedPickPlaceColumn(
                viewModel = viewModel,
                uiState = uiState,
                modifier = Modifier.padding(
                    start = dimensionResource(id = R.dimen.padding_place_card),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                    end = dimensionResource(id = R.dimen.padding_medium)
                )
            )
        }

    }
}

@Composable
private fun ExpandedPickPlaceColumn(
    viewModel: MyCityViewModel,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        uiState.currentCategory!!.list.forEach {
            PlaceCard(
                place = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = dimensionResource(
                            id = R.dimen.padding_small
                        ),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    ),
                onClick = {
                    viewModel.updateCurrentPlace(it)
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ExpandedPickCategoryScreen(
    viewModel: MyCityViewModel,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))) {
        items(uiState.categories) {
            val animatedColor by animateColorAsState(
                if (it.name == uiState.currentCategory?.name) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onTertiaryContainer,
                label = "color"
            )
            ExpandedCategoryCard(
                viewModel = viewModel,
                uiState = uiState,
                category = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        bottom = dimensionResource(
                            id = R.dimen.padding_small
                        ),
                        start = dimensionResource(id = R.dimen.padding_medium),
                        end = dimensionResource(id = R.dimen.padding_medium)
                    )
                    .animateItemPlacement(),
                colors = CardDefaults.cardColors(
                    containerColor = animatedColor
                )
            )
        }
    }
}

@Preview
@Composable
fun ExpandedScreenPreview() {
    MyCityTheme {
        ExpandedPickCategoryScreen(viewModel = viewModel(), uiState = MyCityUiState())
    }
}