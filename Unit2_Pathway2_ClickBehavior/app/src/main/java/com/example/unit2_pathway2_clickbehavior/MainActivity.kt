package com.example.unit2_pathway2_clickbehavior

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.unit2_pathway2_clickbehavior.ui.theme.Unit2_Pathway2_ClickBehaviorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            Unit2_Pathway2_ClickBehaviorTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableStateOf(1) }
    var squeezeCount by remember { mutableStateOf(0) }

    val steps = listOf(
        LemonStep(
            text = R.string.lemon_select,
            image = R.drawable.lemon_tree,
            contentDesc = R.string.lemon_tree_content_description,
            onClick = {
                currentStep = 2
                squeezeCount = (2..4).random()
            }
        ),
        LemonStep(
            text = R.string.lemon_squeeze,
            image = R.drawable.lemon_squeeze,
            contentDesc = R.string.lemon_content_description,
            onClick = {
                squeezeCount--
                if (squeezeCount == 0) currentStep = 3
            }
        ),
        LemonStep(
            text = R.string.lemon_drink,
            image = R.drawable.lemon_drink,
            contentDesc = R.string.lemonade_content_description,
            onClick = { currentStep = 4 }
        ),
        LemonStep(
            text = R.string.lemon_empty_glass,
            image = R.drawable.lemon_restart,
            contentDesc = R.string.empty_glass_content_description,
            onClick = { currentStep = 1 }
        )
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Lemonade", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            steps[currentStep - 1].Run()
        }
    }
}

@Composable
fun LemonTextAndImage(
    textRes: Int,
    imageRes: Int,
    contentDescRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = onClick,
                shape = RoundedCornerShape(dimensionResource(R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = stringResource(contentDescRes),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(dimensionResource(R.dimen.button_image_height))
                        .padding(dimensionResource(R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_vertical)))
            Text(
                text = stringResource(textRes),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/** Model class to hold step info */
data class LemonStep(
    val text: Int,
    val image: Int,
    val contentDesc: Int,
    val onClick: () -> Unit
) {
    @Composable
    fun Run() {
        LemonTextAndImage(
            textRes = text,
            imageRes = image,
            contentDescRes = contentDesc,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun LemonPreview() {
    Unit2_Pathway2_ClickBehaviorTheme {
        LemonadeApp()
    }
}
