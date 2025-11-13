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
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.MapCameraState
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
actual fun SelectableMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    selectedPosition: LatLngEntity?,
    onCameraChanged: (MapCameraState) -> Unit,
    onMapClick: (LatLngEntity) -> Unit,
    onLocationAvailable: (LatLngEntity) -> Unit
) {
    val mapView = rememberSelectableMapViewWithLifecycle()
    val context = LocalContext.current
    var googleMapRef by remember { mutableStateOf<GoogleMap?>(null) }
    var selectedLocationMarker by remember { mutableStateOf<Marker?>(null) }
    val currentUserMarker by lazy { createSelectableCustomLocationMarker(context) }
    val fused by lazy { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(Unit) {
        val map = googleMapRef
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if ((fineGranted || coarseGranted) && map != null) {
            fused.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latLng = LatLng(location.latitude, location.longitude)
                    selectedLocationMarker?.remove()
                    selectedLocationMarker = updateSelectableMarker(map = map, latLng = latLng, currentUserMarker = currentUserMarker)
                    onLocationAvailable(LatLngEntity(latLng.latitude, latLng.longitude))
                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                } else {
                    val cts = CancellationTokenSource()
                    fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                        .addOnSuccessListener { current ->
                            if (current != null) {
                                val latLng = LatLng(current.latitude, current.longitude)
                                selectedLocationMarker?.remove()
                                selectedLocationMarker = updateSelectableMarker(map = map, latLng = latLng, currentUserMarker = currentUserMarker)
                                onLocationAvailable(LatLngEntity(latLng.latitude, latLng.longitude))
                                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
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
        delay(300)

        val map = googleMapRef
        val fineGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            if (initialCamera?.latitude.zeroIfNull() <= 0.0 || initialCamera?.longitude.zeroIfNull() <= 0.0) {

                fused.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        selectedLocationMarker?.remove()
                        selectedLocationMarker = updateSelectableMarker(map = map, latLng = latLng, currentUserMarker = currentUserMarker)
                        onLocationAvailable(LatLngEntity(latLng.latitude, latLng.longitude))
                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                    } else {
                        val cts = CancellationTokenSource()
                        fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                            .addOnSuccessListener { current ->
                                if (current != null) {
                                    val latLng = LatLng(current.latitude, current.longitude)
                                    selectedLocationMarker?.remove()
                                    selectedLocationMarker = updateSelectableMarker(map = map, latLng = latLng, currentUserMarker = currentUserMarker)
                                    onLocationAvailable(LatLngEntity(latLng.latitude, latLng.longitude))
                                    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
                                }
                            }
                    }
                }
            } else {
                val cts = CancellationTokenSource()
                fused.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, cts.token)
                    .addOnSuccessListener { current ->
                        if (current != null) {
                            val latLng = LatLng(current.latitude, current.longitude)
                            selectedLocationMarker?.remove()
                            selectedLocationMarker = updateSelectableMarker(map = map, latLng = latLng, currentUserMarker = currentUserMarker)
                            onLocationAvailable(LatLngEntity(latLng.latitude, latLng.longitude))
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

                    val uiSettings = googleMap.uiSettings
                    uiSettings.isMapToolbarEnabled = true
                    uiSettings.isMyLocationButtonEnabled = true
                }

                googleMap.setOnCameraIdleListener {
                    val cam = googleMap.cameraPosition
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
                    // ready
                }

                googleMap.setOnMapClickListener { latLng ->
                    selectedLocationMarker?.remove()
                    selectedLocationMarker = updateSelectableMarker(
                        map = googleMap,
                        latLng = latLng,
                        currentUserMarker = currentUserMarker
                    )

                    onMapClick(LatLngEntity(latLng.latitude, latLng.longitude))
                }
            }
        }
    )
}

@Composable
private fun rememberSelectableMapViewWithLifecycle(): MapView {
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

private fun createSelectableCustomLocationMarker(context: Context): BitmapDescriptor? {
    return try {
        val drawable = ContextCompat.getDrawable(context, R.drawable.ic_marker_my_location_3)
            ?: throw IllegalStateException("ic_marker_my_location_3 not found")

        val scale = 0.85f
        val baseWidth = 40.dpToPx(context)
        val baseHeight = 50.dpToPx(context)
        val width = (baseWidth * scale).toInt().coerceAtLeast(24)
        val height = (baseHeight * scale).toInt().coerceAtLeast(24)

        val bitmap = createBitmap(width, height)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    } catch (e: Exception) {
        null
    }
}

private fun updateSelectableMarker(
    map: GoogleMap?,
    latLng: LatLng,
    currentUserMarker: BitmapDescriptor?
): Marker? {
    return map?.addMarker(
        MarkerOptions()
            .position(latLng)
            .icon(currentUserMarker)
            .anchor(0.5f, 1.0f)
            .flat(false)
    )
}

