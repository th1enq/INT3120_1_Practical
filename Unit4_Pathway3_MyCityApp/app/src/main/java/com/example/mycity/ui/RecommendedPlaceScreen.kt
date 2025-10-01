package com.example.mycity.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.mycity.R
import com.example.mycity.ui.theme.MyCityTheme
import com.example.mycity.ui.theme.Shapes


@Composable
fun PlaceScreen(
    uiState: MyCityUiState, modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier) {
        val (image, card) = createRefs()
        uiState.currentPlace?.let {
            AnimatedContent(targetState = it.photo, label = "",
                transitionSpec = {
                    fadeIn(
                        animationSpec = tween(durationMillis = 500)
                    ) togetherWith fadeOut(
                        animationSpec = tween(durationMillis = 500)
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    }) { targetPhoto ->
                Image(
                    painter = painterResource(id = targetPhoto),
                    contentScale = ContentScale.FillWidth,
                    contentDescription = null
                )
            }
        }

        Card(shape = RoundedCornerShape(topEnd = dimensionResource(id = R.dimen.padding_place_card)),
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMediumLow
                    )
                )
                .constrainAs(card) {
                    top.linkTo(image.bottom, margin = (-60).dp)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .shadow(
                    dimensionResource(id = R.dimen.shadow_elevation),
                    shape = Shapes.large,
                    ambientColor = Color.Cyan
                )) {

            Text(
                text = stringResource(id = uiState.currentPlace!!.name),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(
                    end = dimensionResource(id = R.dimen.padding_place_card),
                    bottom = dimensionResource(id = R.dimen.padding_small),
                    top = dimensionResource(id = R.dimen.padding_large),
                    start = dimensionResource(id = R.dimen.padding_medium)
                )
            )
            Row(
                verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(
                    end = dimensionResource(id = R.dimen.padding_place_card),
                    bottom = dimensionResource(id = R.dimen.padding_large),
                    start = dimensionResource(id = R.dimen.padding_medium)
                )

            ) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.location_icon),
                    contentDescription = null
                )
                Text(
                    text = stringResource(id = uiState.currentPlace.address),
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Text(
                text = stringResource(id = uiState.currentPlace.description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                modifier = Modifier
                    .padding(
                        end = dimensionResource(id = R.dimen.padding_large),
                        start = dimensionResource(id = R.dimen.padding_medium)
                    )
                    .verticalScroll(rememberScrollState())
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
fun PlaceScreenPreview() {
    MyCityTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            PlaceScreen(uiState = MyCityUiState(), modifier = Modifier.fillMaxSize())
        }
    }

}