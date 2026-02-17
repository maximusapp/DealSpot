package com.app.dealspot.presentation.ui.components

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.app.dealspot.domain.model.DealEntity
import com.app.dealspot.domain.model.MapCameraState
import com.app.dealspot.extensions.dpToPx
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
import kotlinx.coroutines.delay

@SuppressLint("LocalContextResourcesRead")
@Composable
@OptIn(ExperimentalPermissionsApi::class)
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit,
    goToCurrentLocationTrigger: Int,
    deals: List<DealEntity>,
    selectedDeal: DealEntity?,
    onDealSelected: (DealEntity?) -> Unit
) {
    val mapView = rememberMapViewWithLifecycle()
    val context = LocalContext.current
    var googleMapRef by remember { mutableStateOf<GoogleMap?>(null) }
    var dealMarkers by remember { mutableStateOf<List<Marker>>(emptyList()) }
    val fused by lazy { LocationServices.getFusedLocationProviderClient(context) }
    
    // Handle "go to current location" action when trigger changes
    LaunchedEffect(goToCurrentLocationTrigger) {
        if (goToCurrentLocationTrigger > 0) {
            val map = googleMapRef
            val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
            
            if ((fineGranted || coarseGranted) && map != null) {
                fused.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        println("AppMap. location.latitude: ${location.latitude}, location.longitude: ${location.longitude}")
                        val latLng = LatLng(location.latitude, location.longitude)
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                    } else {
                        val cts = CancellationTokenSource()
                        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                            .addOnSuccessListener { current ->
                                if (current != null) {
                                    val latLng = LatLng(current.latitude, current.longitude)
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                }
                            }
                    }
                }
            }
        }
    }

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
        delay(300)

        val map = googleMapRef
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        println("fineGranted: $fineGranted, coarseGranted: $coarseGranted")
        if (fineGranted || coarseGranted) {
            println("latitude and longitude <= 0")

            fused.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    println("lastLocation: is not null: $location")

                    val latLng = LatLng(location.latitude, location.longitude)
                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                } else {
                    println("lastLocation: is null. ELSE case")
                    val cts = CancellationTokenSource()
                    fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                        .addOnSuccessListener { current ->
                            if (current != null) {
                                val latLng = LatLng(current.latitude, current.longitude)
                                map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                            }
                        }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            view.getMapAsync { googleMap ->
                if (googleMapRef?.cameraPosition != googleMap.cameraPosition) {
                    googleMapRef = googleMap
                }

                runCatching {
                    googleMap.isMyLocationEnabled = true
                    googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(view.context, R.raw.map_style))
                    
                    // Disable system icons on Map when click on my marker
                    val uiSettings = googleMap.uiSettings
                    uiSettings.isMapToolbarEnabled = false // Disables toolbar with system icons
                    uiSettings.isMyLocationButtonEnabled = false // Already disabled but ensure it's off
                }.onFailure { e ->
                    println("Error occur when try: INIT_MAP, Error: ${e.message}")
                }

                googleMap.setOnMarkerClickListener { marker ->
                    val deal = marker.tag as? DealEntity
                    println("setOnMarkerClickListener. Marker clicked:${deal?.serviceName}, userSub:${deal?.userSub}")

                    if (deal != null) onDealSelected(deal)
                    true
                }

                // Restore initial camera position immediately if available (before map renders)
                val latLng = LatLng(initialCamera?.latitude.zeroIfNull(), initialCamera?.longitude.zeroIfNull())
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, initialCamera?.zoom.zeroIfNull()))

                googleMap.setOnCameraIdleListener {
                    val cam = googleMap.cameraPosition
                    println("setOnCameraIdleListener. latitude: ${cam.target.latitude}, longitude: ${cam.target.longitude}")

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

                googleMap.setOnMapLoadedCallback {
                    println("setOnMapLoadedCallback. Map loaded")
                    // We can load here another users Deal markers
                }
            }
        }
    )

    }
    
    // Update deal markers when deals list changes
    LaunchedEffect(deals, googleMapRef) {
        val map = googleMapRef
        if (map != null) {
            // Remove old markers
            dealMarkers.forEach { it.remove() }
            
            // Add new markers for each deal
            val newMarkers = deals.mapNotNull { deal ->
                val latitude = deal.latitude ?: return@mapNotNull null
                val longitude = deal.longitude ?: return@mapNotNull null
                
                val serviceId = deal.categoryId?.toInt() ?: 0
                val marker = map.addMarker(
                    MarkerOptions()
                        .position(LatLng(latitude, longitude))
                        .icon(createCustomServiceMarker(context = context, serviceId = serviceId))
                        .anchor(0.5f, 1.0f)
                )
                marker?.tag = deal
                marker
            }
            
            dealMarkers = newMarkers.filterNotNull()
            println("AppMap. Updated ${dealMarkers.size} deal markers")
        }
    }
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
//private fun createCustomLocationMarker(context: Context): BitmapDescriptor? {
//    // Build marker directly from drawable resource (vector or bitmap)
//    try {
//        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_marker_my_location_3)
//            ?: throw IllegalStateException("ic_marker_my_location_2 not found")
//
//        // Slightly reduce size for better visual balance
//        val scale = 0.85f
//        val baseWidth = 40.dpToPx(context)
//        val baseHeight = 50.dpToPx(context)
//        val width = (baseWidth * scale).toInt().coerceAtLeast(24)
//        val height = (baseHeight * scale).toInt().coerceAtLeast(24)
//
//        val bitmap = createBitmap(width, height)
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, width, height)
//        drawable.draw(canvas)
//        return BitmapDescriptorFactory.fromBitmap(bitmap)
//    } catch (e: Exception) {
//        println("Error in createCustomLocationMarker. Error: ${e.message}")
//        return null
//    }
//}

private fun createCustomServiceMarker(context: Context, serviceId: Int): BitmapDescriptor? {
    // Build marker directly from drawable resource (vector or bitmap)
    try {
        val drawable = ContextCompat.getDrawable(context, categoryMarker(serviceId = serviceId))
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

fun categoryMarker(serviceId: Int): Int {
    println("categoryMarker. serviceId: $serviceId")
    return when (serviceId) {
        1 -> R.drawable.ic_household_marker
        2 -> R.drawable.ic_technical_marker
        3 -> R.drawable.ic_car_service_marker
        4 -> R.drawable.ic_builder_marker
        5 -> R.drawable.ic_software_marker
        6 -> R.drawable.ic_lifestyle_marker
        7 -> R.drawable.ic_health_marker
        8 -> R.drawable.ic_food_marker
        9 -> R.drawable.ic_finance_marker
        10 -> R.drawable.ic_legal_marker
        11 -> R.drawable.ic_creative_marker
        12 -> R.drawable.ic_other_marker
        else -> R.drawable.ic_technical_marker
    }
}

private fun updateCurrentUserMarker(map: GoogleMap?, latLng: LatLng, currentUserMarker: BitmapDescriptor?): Marker? {
    return map?.addMarker(
        MarkerOptions()
            .position(latLng)
            .icon(currentUserMarker)
            .anchor(0.5f, 1.0f) // Anchor at bottom center (tip of pin)
            .flat(false)
            .title("My Location")
    )
}
