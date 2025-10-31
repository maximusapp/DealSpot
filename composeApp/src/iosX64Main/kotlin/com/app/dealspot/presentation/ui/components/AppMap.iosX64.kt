@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSCameraPosition

@Composable
actual fun AppMap(modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            val cameraPosition = GMSCameraPosition.cameraWithLatitude(
                1.3588227,
                103.8742114,
                6.0F
            )

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
            mapView.setMapStyle(style)
            val cameraUpdate = GMSCameraUpdate.setCamera(cameraPosition)
            mapView.moveCamera(cameraUpdate)
            mapView
        },
        update = { _ -> }
    )
}


