@file:OptIn(kotlinx.cinterop.ExperimentalForeignApi::class)

package com.app.dealspot.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import cocoapods.GoogleMaps.GMSCameraPosition
import cocoapods.GoogleMaps.GMSCameraUpdate
import cocoapods.GoogleMaps.GMSMapStyle
import cocoapods.GoogleMaps.GMSMapView
import cocoapods.GoogleMaps.GMSMarker
import cocoapods.GoogleMaps.CLLocationCoordinate2D
import com.app.dealspot.data.model.LatLngEntity
import com.app.dealspot.data.model.MapCameraState
import com.app.dealspot.presentation.utils.zeroIfNull
import kotlinx.cinterop.*
import kotlinx.cinterop.useContents
import platform.CoreGraphics.*
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.darwin.NSObject
import platform.UIKit.*

/**
 * Creates a marker image from asset, scaled similar to Android
 */
private fun createCustomLocationMarker(): UIImage? {
    val original = UIImage.imageNamed("ic_marker_my_location_3") ?: return null
    val baseWidth = 56.0
    val baseHeight = 72.0
    val scale = 0.85
    val targetSize = CGSizeMake((baseWidth * scale).coerceAtLeast(20.0), (baseHeight * scale).coerceAtLeast(20.0))
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
    onCameraChanged: (MapCameraState) -> Unit,
    goToCurrentLocationTrigger: Int
) {
    println("AppMap.iosSimulatorArm64")
    val mapViewState = remember { mutableStateOf<GMSMapView?>(null) }
    val locationMarkerState = remember { mutableStateOf<GMSMarker?>(null) }
    
    // Handle "go to current location" trigger
    LaunchedEffect(goToCurrentLocationTrigger) {
        val mapView = mapViewState.value
        if (goToCurrentLocationTrigger > 0 && mapView != null) {
            val locationManager = CLLocationManager()
            
            class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    val map = mapViewState.value ?: return
                    
                    last.coordinate.useContents {
                        locationMarkerState.value?.map = null
                        val marker = GMSMarker()
                        memScoped {
                            val coordPtr = alloc<cocoapods.GoogleMaps.CLLocationCoordinate2D>()
                            coordPtr.latitude = latitude.toDouble()
                            coordPtr.longitude = longitude.toDouble()
                            marker.position = coordPtr.readValue()
                        }
                        
                        val customIcon = createCustomLocationMarker()
                        if (customIcon != null) {
                            marker.icon = customIcon
                            marker.groundAnchor = CGPointMake(0.5, 1.0)
                        }
                        marker.map = map
                        marker.title = "My Location"
                        locationMarkerState.value = marker
                        
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        map.animateWithCameraUpdate(update)
                    }
                    manager.stopUpdatingLocation()
                }
            }
            val delegate = DelegateImpl()
            locationManager.delegate = delegate
            locationManager.requestWhenInUseAuthorization()
            locationManager.startUpdatingLocation()
        }
    }
    
    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            mapViewState.value = mapView
            mapView.myLocationEnabled = false // Use custom marker instead
            var didCenter = initialCamera?.latitude?.zeroIfNull() ?: -1.0 > 0.0 || initialCamera?.longitude?.zeroIfNull() ?: -1.0 > 0.0

            val style = GMSMapStyle()
            mapView.mapStyle = style
            mapView.setPadding(UIEdgeInsetsMake(0.0, 0.0, 96.0, 0.0))

            // Custom location marker
            var locationMarker: GMSMarker? = null
            locationMarkerState.value = locationMarker

            // Helper to update custom location marker
            fun updateLocationMarker(lat: Double, lng: Double) {
                locationMarker?.map = null
                locationMarkerState.value?.map = null
                val marker = GMSMarker()
                memScoped {
                    val coordPtr = alloc<cocoapods.GoogleMaps.CLLocationCoordinate2D>()
                    coordPtr.latitude = lat
                    coordPtr.longitude = lng
                    marker.position = coordPtr.readValue()
                }
                val customIcon = createCustomLocationMarker()
                if (customIcon != null) {
                    marker.icon = customIcon
                    marker.groundAnchor = CGPointMake(0.5, 1.0)
                }
                marker.map = mapView
                marker.title = "My Location"
                locationMarker = marker
                locationMarkerState.value = marker
            }

            // Apply initial camera if provided
            if (initialCamera?.latitude?.zeroIfNull() ?: -1.0 > 0.0 && initialCamera?.longitude?.zeroIfNull() ?: -1.0 > 0.0) {
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
                        updateLocationMarker(latitude.toDouble(), longitude.toDouble())
                        
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)

                        if (!didCenter) {
                            mapView.moveCamera(update)
                            didCenter = true
                        }

                        onCameraChanged(
                            MapCameraState(
                                latitude = latitude.toDouble(),
                                longitude = longitude.toDouble(),
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
        update = { view ->
            mapViewState.value = view
        }
    )
}
