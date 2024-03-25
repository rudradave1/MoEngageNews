package com.rudra.moengagenews

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.rudra.moengagenews.ui.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val permissionState = ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            // If the permission is not granted, request it.
            if (permissionState == PackageManager.PERMISSION_DENIED ) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
            }
            MainScreen()
        }
    }
}
