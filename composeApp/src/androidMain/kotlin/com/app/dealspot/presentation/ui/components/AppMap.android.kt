package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.app.dealspot.R
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.MapStyleOptions

@Composable
actual fun AppMap(modifier: Modifier) {
    val mapView = rememberMapViewWithLifecycle()

    AndroidView(
        modifier = modifier,
        factory = { mapView },
        update = { view ->
            view.getMapAsync { googleMap -> // Apply grey style from raw resource
                try {
                    googleMap.setMapStyle(
                        MapStyleOptions.loadRawResourceStyle(view.context, R.raw.map_style)
                    )
                } catch (_: Exception) {
                }
            }
        }
    )
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