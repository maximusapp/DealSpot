@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.CLLocationCoordinate2D
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.MapCameraState
import kotlinx.cinterop.*
import platform.CoreGraphics.*
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject
import platform.UIKit.*

private fun createCustomLocationMarker(): UIImage? {
    // Use image asset directly, scaled similarly to Android (base 56x72pt, ~85%)
    val name = "ic_marker_my_location_3"
    val original = UIImage.imageNamed(name) ?: return null

    val baseWidth = 56.0
    val baseHeight = 72.0
    val scale = 0.85
    val targetSize = CGSizeMake(
        (baseWidth * scale).coerceAtLeast(20.0),
        (baseHeight * scale).coerceAtLeast(20.0)
    )

    UIGraphicsBeginImageContextWithOptions(targetSize, false, 0.0)
    original.drawInRect(CGRectMake(0.0, 0.0, targetSize.useContents { width }, targetSize.useContents { height }))
    val resized = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    return resized
}

@Composable
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit
) {
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            var didCenter = initialCamera != null
            // Disable default location dot - using custom marker
            mapView.myLocationEnabled = false

            val style = GMSMapStyle()
            mapView.mapStyle = style
            mapView.setPadding(UIEdgeInsetsMake(0.0, 0.0, 96.0, 0.0))

            // Custom location marker
            var locationMarker: GMSMarker? = null

            // Helper to update custom location marker
            fun updateLocationMarker(lat: Double, lng: Double) {
                locationMarker?.map = null // Remove old marker
                val marker = GMSMarker()
                memScoped {
                    val coordPtr = alloc<cocoapods.GoogleMaps.CLLocationCoordinate2D>()
                    coordPtr.latitude = lat
                    coordPtr.longitude = lng
                    marker.position = coordPtr.readValue()
                }
                
                // Set custom icon
                val customIcon = createCustomLocationMarker()
                if (customIcon != null) {
                    marker.icon = customIcon
                    marker.groundAnchor = CGPointMake(0.5, 1.0) // Anchor at bottom center (tip of pin)
                }
                
                marker.map = mapView
                marker.title = "My Location"
                locationMarker = marker
            }

            if (initialCamera != null) {
                val cam = GMSCameraPosition.cameraWithLatitude(initialCamera.latitude, initialCamera.longitude, initialCamera.zoom)
                val update = GMSCameraUpdate.setCamera(cam)
                mapView.moveCamera(update)
                didCenter = true
            }

            // Zoom to current location
            val locationManager = CLLocationManager()
            class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return

                    last.coordinate.useContents {
                        // Update custom location marker
                        updateLocationMarker(latitude.toDouble(), longitude.toDouble())
                        
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        if (!didCenter) {
                            mapView.moveCamera(update)
                            didCenter = true
                        }
                        onCameraChanged(
                            MapCameraState(
                                latitude.toDouble(),
                                longitude.toDouble(),
                                14.0f
                            )
                        )
                    }

                    manager.stopUpdatingLocation()
                }
            }
            val delegate = DelegateImpl()
            locationManager.delegate = delegate
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()

            // Delegate for camera changes
            class MapDelegate: NSObject(), cocoapods.GoogleMaps.GMSMapViewDelegateProtocol {
                override fun mapView(mapView: GMSMapView, didChangeCameraPosition: GMSCameraPosition) {
                    if (didChangeCameraPosition.zoom >= 2f) {
                        onCameraChanged(
                            didChangeCameraPosition.target.useContents {
                                MapCameraState(
                                    latitude = this.latitude,
                                    longitude = this.longitude,
                                    zoom = didChangeCameraPosition.zoom
                                )
                            }
                        )
                    }
                }
            }
            mapView.delegate = MapDelegate()
            mapView
        },
        update = { _ -> }
    )
}
