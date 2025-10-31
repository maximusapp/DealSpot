@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView

@Composable
actual fun AppMap(modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            val json = """
            [
              { "elementType": "geometry", "stylers": [{ "saturation": -100 }, { "lightness": 10 }] },
              { "elementType": "labels.icon", "stylers": [{ "visibility": "off" }] },
              { "featureType": "road", "elementType": "geometry", "stylers": [{ "saturation": -100 }] },
              { "featureType": "poi", "elementType": "geometry", "stylers": [{ "saturation": -100 }] },
              { "featureType": "water", "elementType": "geometry", "stylers": [{ "saturation": -100 }] }
            ]
            """.trimIndent()
            val style = GMSMapStyle()
            mapView.mapStyle = style
            mapView
        },
        update = { _ -> }
    )
}


