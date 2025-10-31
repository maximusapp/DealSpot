@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import kotlinx.cinterop.useContents
import platform.UIKit.UIEdgeInsetsMake
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

@Composable
actual fun AppMap(modifier: Modifier) {
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            mapView.myLocationEnabled = true

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
            // Move built-in controls up from the bottom to clear bottom navigation
            mapView.setPadding(UIEdgeInsetsMake(0.0, 0.0, 96.0, 0.0))

            // Setup location manager to zoom to user's current location
            val locationManager = CLLocationManager()
            class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    last.coordinate.useContents {
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        mapView.moveCamera(update)
                    }

                    manager.stopUpdatingLocation()
                }
            }
            val delegate = DelegateImpl()
            locationManager.delegate = delegate
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()

            mapView
        },
        update = { _ -> }
    )
}



