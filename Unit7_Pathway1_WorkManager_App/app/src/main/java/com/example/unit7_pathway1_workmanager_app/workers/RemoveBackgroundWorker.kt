package com.example.bluromatic.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.bluromatic.DELAY_TIME_MILLIS
import com.example.bluromatic.KEY_IMAGE_URI
import com.example.bluromatic.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.math.abs

private const val TAG = "RemoveBackgroundWorker"

/**
 * Worker class that removes background from an image (simulated by making background transparent)
 */
class RemoveBackgroundWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.removing_background),
            applicationContext
        )

        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        return withContext(Dispatchers.IO) {
            try {
                if (resourceUri.isNullOrBlank()) {
                    Log.e(TAG, "Invalid input uri")
                    throw IllegalArgumentException(
                        applicationContext.resources.getString(R.string.invalid_input_uri)
                    )
                }

                val resolver = applicationContext.contentResolver
                val picture = BitmapFactory.decodeStream(
                    resolver.openInputStream(Uri.parse(resourceUri))
                )

                // Simulate background removal processing time (1 second as requested)
                delay(1000)

                val output = removeBackground(picture)

                // Write bitmap to a temp file
                val outputUri = writeBitmapToFile(applicationContext, output)

                makeStatusNotification(
                    "Background removed successfully",
                    applicationContext
                )

                val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())

                Result.success(outputData)
            } catch (throwable: Throwable) {
                Log.e(
                    TAG,
                    applicationContext.resources.getString(R.string.error_removing_background),
                    throwable
                )
                Result.failure()
            }
        }
    }

    /**
     * Simulates background removal by making lighter areas transparent
     * This is a simplified version - real background removal would use ML models
     */
    private fun removeBackground(picture: Bitmap): Bitmap {
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
                val colorDistance = abs(red - bgRed) + abs(green - bgGreen) + abs(blue - bgBlue)
                
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
}
