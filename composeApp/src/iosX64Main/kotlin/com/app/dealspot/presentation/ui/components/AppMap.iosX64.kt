@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSCameraPosition
import com.app.dealspot.data.model.MapCameraState
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject
import platform.UIKit.UIEdgeInsetsMake

@Composable
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit
) {
    println("AppMap.iosX64")
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            var didCenter = initialCamera != null
            val style = GMSMapStyle()
            mapView.setMapStyle(style)
            mapView.setPadding(UIEdgeInsetsMake(96.0, 0.0, 0.0, 0.0))

            if (initialCamera != null) {
                val cam = GMSCameraPosition.cameraWithLatitude(initialCamera.latitude, initialCamera.longitude, initialCamera.zoom)
                val update = GMSCameraUpdate.setCamera(cam)
                mapView.moveCamera(update)
                didCenter = true
            }

            val locationManager = CLLocationManager()
            class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    last.coordinate.useContents {
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        if (!didCenter) {
                            mapView.moveCamera(update)
                            didCenter = true
                        }

                        onCameraChanged(
                            MapCameraState(
                                latitude = latitude,
                                longitude = longitude,
                                zoom = 14.0f
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

            class MapDelegate: NSObject(), cocoapods.GoogleMaps.GMSMapViewDelegateProtocol {
                override fun mapView(mapView: GMSMapView, didChangeCameraPosition: GMSCameraPosition) {
                    didChangeCameraPosition.target.useContents {
                        onCameraChanged(
                            MapCameraState(
                                latitude = this.latitude,
                                longitude = this.longitude,
                                zoom = didChangeCameraPosition.zoom
                            )
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
