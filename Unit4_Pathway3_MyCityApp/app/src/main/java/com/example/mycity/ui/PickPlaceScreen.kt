package com.example.mycity.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycity.R
import com.example.mycity.data.Place
import com.example.mycity.ui.theme.Shapes


@Composable
fun PickPlaceScreen(
    viewModel: MyCityViewModel,
    navigateFunction: () -> Unit,
    uiState: MyCityUiState,
    modifier: Modifier = Modifier
) {
    var visible by remember {
        mutableStateOf(true)
    }
    var index = 1
    AnimatedContent(targetState = uiState.currentCategory,
        label = "",
        transitionSpec = {
            (slideInVertically(animationSpec = tween(durationMillis = 1000)) { height -> height }
                    + fadeIn(animationSpec = tween(durationMillis = 500)))
                .togetherWith(slideOutVertically(animationSpec = tween(durationMillis = 1000)) { height -> -height } + fadeOut(
                    animationSpec = tween(durationMillis = 500)
                ))

        }) {
        LazyColumn(modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))) {
            items(it!!.list) {
                AnimatedVisibility(
                    visible = visible,
                    exit = slideOutHorizontally(animationSpec = tween(durationMillis = 500 * index)) { full ->
                        -3 * full
                    }) {
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
                            visible = false
                            viewModel.updateCurrentPlace(it)
                            navigateFunction()
                        }
                    )
                    index++
                }
            }
        }

    }

}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceCard(
    place: Place,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = { onClick() },
        shape = Shapes.medium,
    ) {
        Row(
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = place.photo), contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_small))
                    .size(70.dp)
                    .clip(shape = Shapes.small)
            )
            Text(
                text = stringResource(id = place.name),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }


}

@Preview
@Composable
fun PlaceCardPreview() {
    PlaceCard(
        place = Place(
            name = R.string.kavkaz_title,
            description = R.string.kavkaz_description,
            address = R.string.kavkaz_address,
            photo = R.drawable.kavkaz
        )
    )

}