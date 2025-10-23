package com.example.bluromatic.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.bluromatic.IMAGE_MANIPULATION_WORK_NAME
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import com.example.bluromatic.getImageUri
import com.example.bluromatic.workers.blurBitmap
import com.example.unit7_pathway1_workmanager_app.ui.theme.Unit7_Pathway1_WorkManager_AppTheme
import kotlinx.coroutines.delay
import android.graphics.Color

class ImageProgressActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Unit7_Pathway1_WorkManager_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImageProgressScreen()
                }
            }
        }
    }
}

@Composable
fun ImageProgressScreen() {
    val context = LocalContext.current
    val workManager = WorkManager.getInstance(context)
    
    var currentStage by remember { mutableStateOf("Starting...") }
    var progress by remember { mutableFloatStateOf(0f) }
    var currentBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isCompleted by remember { mutableStateOf(false) }

    // Simulate progressive stages for demo (1 second each as requested)
    LaunchedEffect(Unit) {
        try {
            // Load original image first
            val originalUri = context.getImageUri()
            val inputStream = context.contentResolver.openInputStream(originalUri)
            val originalBitmap = BitmapFactory.decodeStream(inputStream)
            currentBitmap = originalBitmap
            
            // Simulate progressive stages
            val stages = listOf(
                "Starting..." to 0.1f,
                "Applying light blur..." to 0.25f,
                "Applying medium blur..." to 0.5f,
                "Applying heavy blur..." to 0.75f,
                "Removing background..." to 0.9f,
                "Completed!" to 1.0f
            )

            stages.forEachIndexed { index, (stage, progressValue) ->
                currentStage = stage
                progress = progressValue
                
                // Generate and display progressive images
                when (index) {
                    1 -> currentBitmap = blurBitmap(originalBitmap, 1)
                    2 -> currentBitmap = blurBitmap(originalBitmap, 2)
                    3 -> currentBitmap = blurBitmap(originalBitmap, 3)
                    4 -> currentBitmap = simulateRemoveBackground(originalBitmap)
                    5 -> isCompleted = true
                }
                
                delay(1000) // 1 second per stage as requested
            }

        } catch (e: Exception) {
            Log.e("ImageProgress", "Error in progress simulation", e)
            currentStage = "Error occurred"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        
        // Display current image or placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentAlignment = Alignment.Center
        ) {
            if (currentBitmap != null) {
                Image(
                    bitmap = currentBitmap!!.asImageBitmap(),
                    contentDescription = "Processing image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.android_cupcake),
                    contentDescription = "Original image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Progress indicator
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
        )

        // Current stage text
        Text(
            text = currentStage,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )

        // Progress percentage
        Text(
            text = "${(progress * 100).toInt()}%",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Simulates background removal by making lighter areas transparent
 */
private fun simulateRemoveBackground(picture: Bitmap): Bitmap {
    val width = picture.width
    val height = picture.height
    val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

    val backgroundColor = picture.getPixel(0, 0)
    val bgRed = Color.red(backgroundColor)
    val bgGreen = Color.green(backgroundColor)
    val bgBlue = Color.blue(backgroundColor)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel = picture.getPixel(x, y)
            
            // Get RGB values
            val red = Color.red(pixel)
            val green = Color.green(pixel)
            val blue = Color.blue(pixel)
            
            // Calculate brightness
            val brightness = (red + green + blue) / 3
            val colorDistance = kotlin.math.abs(red - bgRed) + kotlin.math.abs(green - bgGreen) + kotlin.math.abs(blue - bgBlue)
            
            // If pixel is bright (likely background), make it transparent
            // Otherwise keep the original color
            if (brightness > 220 || colorDistance < 80) {
                output.setPixel(x, y, Color.TRANSPARENT)
            } else {
                output.setPixel(x, y, pixel)
            }
        }
    }

    return output
}