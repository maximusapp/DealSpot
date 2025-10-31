package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.app.dealspot.R
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@SuppressLint("LocalContextResourcesRead")
@Composable
@OptIn(ExperimentalPermissionsApi::class)
actual fun AppMap(modifier: Modifier) {
    val mapView = rememberMapViewWithLifecycle()
    val context = LocalContext.current
    var googleMapRef by remember { mutableStateOf<GoogleMap?>(null) }
    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(Unit) {
        if (!permissionsState.allPermissionsGranted) {
            permissionsState.launchMultiplePermissionRequest()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            view.getMapAsync { googleMap -> // Apply grey style from raw resource
                googleMapRef = googleMap
                try {
                    googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(view.context, R.raw.map_style)
                    )
                } catch (_: Exception) {
                }

                // Add bottom padding so the My Location button sits above bottom navigation
                val density = context.resources.displayMetrics.density
                val bottomPx = (96 * density).toInt() // ~96dp to clear nav bar + extra space
                googleMap.setPadding(0, bottomPx, 0, 0)

                val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                if (fineGranted || coarseGranted) {
                    try { googleMap.isMyLocationEnabled = true } catch (_: SecurityException) {}
                    val fused = LocationServices.getFusedLocationProviderClient(context)
                    fused.lastLocation.addOnSuccessListener { location ->
                        if (location != null) {
                            val latLng = LatLng(location.latitude, location.longitude)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                        }
                    }
                }
            }
        }
    )

    LaunchedEffect(permissionsState.allPermissionsGranted, googleMapRef) {
        val map = googleMapRef ?: return@LaunchedEffect
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        if (fineGranted || coarseGranted) {
            try { map.isMyLocationEnabled = true } catch (_: SecurityException) {}
            val fused = LocationServices.getFusedLocationProviderClient(context)
            fused.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                }
            }
        }
    }
}

@Composable
private fun rememberMapViewWithLifecycle(): MapView {
    val context = androidx.compose.ui.platform.LocalContext.current
    val mapView = remember { MapView(context) }

    DisposableEffect(Unit) {
        mapView.onCreate(null)
        mapView.onStart()
        mapView.onResume()
        onDispose {
            mapView.onPause()
            mapView.onStop()
            mapView.onDestroy()
        }
    }
    return mapView
}
