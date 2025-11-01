package com.app.dealspot.presentation.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.app.dealspot.R
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.presentation.utils.zeroIfNull
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("LocalContextResourcesRead")
@Composable
@OptIn(ExperimentalPermissionsApi::class)
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit
) {
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

    LaunchedEffect(permissionsState.allPermissionsGranted) {
        println("allPermissionsGranted") // print 4 times
        val map = googleMapRef
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        println("fineGranted: $fineGranted, coarseGranted: $coarseGranted")
        if (fineGranted || coarseGranted) {
            try { map?.isMyLocationEnabled = true } catch (_: SecurityException) {}

            val fused = LocationServices.getFusedLocationProviderClient(context)

            if (initialCamera?.latitude.zeroIfNull() < 0.0 || initialCamera?.longitude.zeroIfNull() < 0.0) {
                println("allPermissionsGranted animateCamera: $initialCamera") // print 4 times

                fused.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                    } else {
                        val cts = CancellationTokenSource()
                        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                            .addOnSuccessListener { current ->
                                if (current != null) {
                                    val latLng = LatLng(current.latitude, current.longitude)
                                    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                }
                            }
                    }
                }
            }
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            view.getMapAsync { googleMap ->
                googleMapRef = googleMap

                runCatching {
                    googleMap.isMyLocationEnabled = true
                    googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(view.context, R.raw.map_style))
                }.onFailure { e ->
                    println("Error occur when try: INIT_MAP, Error: ${e.message}")
                }

                googleMap.setOnCameraIdleListener {
                    val cam = googleMap.cameraPosition
                    onCameraChanged(
                        MapCameraState(
                            latitude = cam.target.latitude,
                            longitude = cam.target.longitude,
                            zoom = cam.zoom,
                            bearing = cam.bearing,
                            tilt = cam.tilt
                        )
                    )
                }

                // Add bottom padding so the My Location button sits above bottom navigation
                val density = context.resources.displayMetrics.density
                val topPx = (96 * density).toInt() // ~96dp top padding
                googleMap.setPadding(0, topPx, 0, 0)

                if (initialCamera?.latitude.zeroIfNull() > 0.0 || initialCamera?.longitude.zeroIfNull() > 0.0) {
                    println("AndroidView. initialCamera: $initialCamera") // print 2 times

                    val latLng = LatLng(initialCamera?.latitude.zeroIfNull(), initialCamera?.longitude.zeroIfNull())
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, initialCamera?.zoom.zeroIfNull()))
                } else {
                    val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                    if (fineGranted || coarseGranted) {
                        try { googleMap.isMyLocationEnabled = true } catch (_: SecurityException) {}

                        val fused = LocationServices.getFusedLocationProviderClient(context)
                        fused.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val latLng = LatLng(location.latitude, location.longitude)
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                            } else {
                                // Fallback to a fresh location query if lastLocation is null
                                val cts = CancellationTokenSource()
                                fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                                    .addOnSuccessListener { current ->
                                        if (current != null) {
                                            val latLng = LatLng(current.latitude, current.longitude)
                                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                        } else {
                                            // Final fallback: one-shot updates
                                            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1500L)
                                                .setMinUpdateIntervalMillis(500L)
                                                .setMaxUpdates(1)
                                                .build()
                                            val callback = object : LocationCallback() {
                                                override fun onLocationResult(result: LocationResult) {
                                                    val loc = result.lastLocation
                                                    if (loc != null) {
                                                        val latLng = LatLng(loc.latitude, loc.longitude)
                                                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                                    }
                                                    fused.removeLocationUpdates(this)
                                                }
                                            }
                                            fused.requestLocationUpdates(request, callback, Looper.getMainLooper())
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
private fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
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
