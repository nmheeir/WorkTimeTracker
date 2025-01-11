package com.example.worktimetracker.ui.screens.check.component

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import com.example.worktimetracker.ui.theme.Typography
import com.google.android.gms.location.LocationServices
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapContent(
    updateCurrentLocation: (Location?) -> Unit
) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    var isMapLoading by remember { mutableStateOf(true) }

    // Request location updates
    LaunchedEffect(fusedLocationClient) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as ComponentActivity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                1
            )
            return@LaunchedEffect
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            currentLocation = location
            isMapLoading = false
            updateCurrentLocation(location)
        }
    }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val boxHeight = screenHeight * 0.3f
    if (isMapLoading) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .height(boxHeight)
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Loading map...", style = Typography.bodyMedium)
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 2.dp)
                .height(boxHeight)
                .clip(RoundedCornerShape(8.dp))
        ) {
            AndroidView(
                factory = { context ->
                    MapView(context).apply {
                        val mapController = this.controller
                        mapController.setZoom(18.0)
                        this.setMultiTouchControls(true)

                        val startPoint = GeoPoint(currentLocation?.latitude ?: 0.0, currentLocation?.longitude ?: 0.0)
                        mapController.setCenter(startPoint)
                        currentLocation?.let { location ->
                            val currentGeoPoint =
                                GeoPoint(location.latitude, location.longitude)
                            val marker = Marker(this)
                            marker.position = currentGeoPoint
                            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            marker.title = "Current Location"
                            this.overlays.add(marker)
                            mapController.setCenter(currentGeoPoint)
                        }
                    }
                }
            )
        }
    }
}