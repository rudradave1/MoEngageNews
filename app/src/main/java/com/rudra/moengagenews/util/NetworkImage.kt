package com.rudra.moengagenews.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun NetworkImage(
    url: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    // Create a bitmap state to hold the loaded image
    val bitmapState = remember { mutableStateOf<Bitmap?>(null) }

    // Create a loading state to indicate whether the image is loading
    val isLoading = remember { mutableStateOf(true) }

    // Load the image asynchronously using a coroutine
    LaunchedEffect(url) {
        val imageBitmap = loadImageFromNetwork(url)
        bitmapState.value = imageBitmap
        isLoading.value = false // Set isLoading to false once the image is loaded
    }

    // Display the loaded image or a progress indicator if loading
    if (isLoading.value) {
        // Show a linear progress indicator while loading
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
            )
        }

    } else {
        bitmapState.value?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                contentScale = contentScale
            )
        }
    }
}


suspend fun loadImageFromNetwork(url: String): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            bitmap
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
} 