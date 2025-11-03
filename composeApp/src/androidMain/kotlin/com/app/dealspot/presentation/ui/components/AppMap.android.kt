package com.app.dealspot.presentation.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
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
import androidx.core.graphics.createBitmap
import com.app.dealspot.R
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.presentation.utils.zeroIfNull
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
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
    var userLocationMarker by remember { mutableStateOf<Marker?>(null) }
    val currentUserMarker by lazy { createCustomLocationMarker(context) }
    val fused by lazy { LocationServices.getFusedLocationProviderClient(context) }
    var currentUserLocation by remember { mutableStateOf<LatLng?>(null) }

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
        println("allPermissionsGranted")
        println("allPermissionsGranted. latitude: ${initialCamera?.latitude}, longitude: ${initialCamera?.longitude}")
        val map = googleMapRef
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        println("fineGranted: $fineGranted, coarseGranted: $coarseGranted")
        if (fineGranted || coarseGranted) {
            if (initialCamera?.latitude.zeroIfNull() < 0.0 || initialCamera?.longitude.zeroIfNull() < 0.0) {
                println("allPermissionsGranted animateCamera: $initialCamera")

                fused.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        // Update custom location marker
                        userLocationMarker?.remove()
                        userLocationMarker = map?.addMarker(
                            MarkerOptions()
                                .position(latLng)
                                .icon(currentUserMarker)
                                .anchor(0.5f, 1.0f) // Anchor at bottom center (tip of pin)
                                .flat(false)
                                .title("My Location")
                        )

                        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                    } else {
                        val cts = CancellationTokenSource()
                        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                            .addOnSuccessListener { current ->
                                if (current != null) {
                                    val latLng = LatLng(current.latitude, current.longitude)

                                    if (currentUserLocation?.latitude.zeroIfNull() != latLng.latitude || currentUserLocation?.longitude.zeroIfNull() != latLng.longitude) {
                                        currentUserLocation = latLng
                                    }

                                    // Update custom location marker
                                    userLocationMarker?.remove()
                                    userLocationMarker = map?.addMarker(
                                        MarkerOptions()
                                            .position(latLng)
                                            .icon(currentUserMarker)
                                            .anchor(0.5f, 1.0f) // Anchor at bottom center (tip of pin)
                                            .flat(false)
                                            .title("My Location")
                                    )

                                    map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                }
                            }
                    }
                }
            }
        }
    }

    LaunchedEffect(currentUserLocation) {
        println("currentUserLocation: $currentUserLocation")
        val cts = CancellationTokenSource()
        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
            .addOnSuccessListener { current ->
                if (current != null) {
                    val latLng = LatLng(current.latitude, current.longitude)
                    // Update custom location marker
                    userLocationMarker?.remove()
                    userLocationMarker = googleMapRef?.addMarker(
                        MarkerOptions()
                            .position(latLng)
                            .icon(currentUserMarker)
                            .anchor(0.5f, 1.0f) // Anchor at bottom center (tip of pin)
                            .flat(false)
                            .title("My Location")
                    )
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
                    googleMap.isMyLocationEnabled = false
                    googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(view.context, R.raw.map_style))
                }.onFailure { e ->
                    println("Error occur when try: INIT_MAP, Error: ${e.message}")
                }

                googleMap.setOnCameraIdleListener {
                    val cam = googleMap.cameraPosition
                    println("setOnCameraIdleListener. latitude: ${cam.target.latitude}, longitude: ${cam.target.longitude}")

                    if (cam.target.latitude > 0.0 && cam.target.longitude > 0.0) {
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
                }

                googleMap.setOnMapLoadedCallback {
                    println("setOnMapLoadedCallback. latitude: ${initialCamera?.latitude}, longitude: ${initialCamera?.longitude}")
                    if (initialCamera?.latitude.zeroIfNull() > 0.0 || initialCamera?.longitude.zeroIfNull() > 0.0) {
                        val latLng = LatLng(initialCamera?.latitude.zeroIfNull(), initialCamera?.longitude.zeroIfNull())
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, initialCamera?.zoom.zeroIfNull()))
                    } else {
                        println("setOnMapLoadedCallback. else case")
                        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

                        if (fineGranted || coarseGranted) {
                            fused.lastLocation.addOnSuccessListener { location ->
                                if (location != null) {
                                    val latLng = LatLng(location.latitude, location.longitude)
                                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                } else {
                                    val cts = CancellationTokenSource()
                                    fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                                        .addOnSuccessListener { current ->
                                            if (current != null) {
                                                val latLng = LatLng(current.latitude, current.longitude)

                                                if (currentUserLocation?.latitude.zeroIfNull() != latLng.latitude || currentUserLocation?.longitude.zeroIfNull() != latLng.longitude) {
                                                    currentUserLocation = latLng
                                                }

                                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                            }
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

/**
 * Creates a custom location marker
 */
private fun createCustomLocationMarker(context: Context): BitmapDescriptor? {
    // Build marker directly from drawable resource (vector or bitmap)
    try {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_marker_my_location_3)
            ?: throw IllegalStateException("ic_marker_my_location_2 not found")

        // Slightly reduce size for better visual balance
        val scale = 0.85f
        val baseWidth = 40.dpToPx(context)
        val baseHeight = 50.dpToPx(context)
        val width = (baseWidth * scale).toInt().coerceAtLeast(24)
        val height = (baseHeight * scale).toInt().coerceAtLeast(24)

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    } catch (e: Exception) {
        println("Error in createCustomLocationMarker. Error: ${e.message}")
        return null
    }
}

/**
 * Converts density-independent pixels to pixels
 */
private fun Int.dpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
