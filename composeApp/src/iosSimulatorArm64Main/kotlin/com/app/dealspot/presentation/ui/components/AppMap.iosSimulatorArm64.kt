@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.presentation.utils.zeroIfNull
import kotlinx.cinterop.useContents
import platform.UIKit.UIEdgeInsetsMake
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject

@Composable
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit
) {
    println("AppMap.iosSimulatorArm64")
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            mapView.myLocationEnabled = true

            val style = GMSMapStyle()
            mapView.mapStyle = style
            // Move built-in controls up from the bottom to clear bottom navigation
            mapView.setPadding(UIEdgeInsetsMake(96.0, 0.0, 0.0, 0.0))

            // Apply initial camera if provided, else will center on first location update
            if (initialCamera?.latitude.zeroIfNull() < 0.0 || initialCamera?.longitude.zeroIfNull() < 0.0) {
                val cam = GMSCameraPosition.cameraWithLatitude(initialCamera?.latitude.zeroIfNull(), initialCamera?.longitude.zeroIfNull(), initialCamera?.zoom.zeroIfNull())
                val update = GMSCameraUpdate.setCamera(cam)
                mapView.moveCamera(update)
            }

            // Setup location manager to zoom to user's current location
            val locationManager = CLLocationManager()
            class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    last.coordinate.useContents {
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)

                        if (initialCamera?.latitude.zeroIfNull() > 0.0 || initialCamera?.longitude.zeroIfNull() > 0.0) {
                            mapView.moveCamera(update)
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

            // Delegate for camera changes
            class MapDelegate: NSObject(), cocoapods.GoogleMaps.GMSMapViewDelegateProtocol {
                override fun mapView(mapView: GMSMapView, didChangeCameraPosition: GMSCameraPosition) {
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

            mapView.delegate = MapDelegate()
            mapView
        },
        update = { _ -> }
    )
}
