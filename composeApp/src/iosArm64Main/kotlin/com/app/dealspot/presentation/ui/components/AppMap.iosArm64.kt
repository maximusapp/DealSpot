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
import cocoapods.GoogleMaps.animateWithCameraUpdate
import com.app.dealspot.domain.model.DealEntity
import com.app.dealspot.domain.model.MapCameraState
import com.app.dealspot.presentation.utils.mapStyle
import com.app.dealspot.presentation.utils.zeroIfNull
import kotlinx.cinterop.useContents
import kotlinx.coroutines.delay
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.darwin.NSObject


@Composable
actual fun AppMap(
    modifier: Modifier,
    initialCamera: MapCameraState?,
    onCameraChanged: (MapCameraState) -> Unit,
    goToCurrentLocationTrigger: Int,
    deals: List<DealEntity>,
    selectedDeal: DealEntity?,
    onDealSelected: (DealEntity?) -> Unit
) {
    val mapViewState = remember { mutableStateOf<GMSMapView?>(null) }
    val locationMarkerState = remember { mutableStateOf<GMSMarker?>(null) }
    val mapDelegateState = remember { mutableStateOf<cocoapods.GoogleMaps.GMSMapViewDelegateProtocol?>(null) }

    // Handle "go to current location" trigger - matches Android behavior
    LaunchedEffect(goToCurrentLocationTrigger) {
        if (goToCurrentLocationTrigger > 0) {
            println("goToCurrentLocationTrigger: $goToCurrentLocationTrigger")
            val mapView = mapViewState.value
            if (mapView != null) {
                val locationManager = CLLocationManager()

                class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                        val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                        val map = mapViewState.value ?: return

                        last.coordinate.useContents {
                            // Animate camera to current location (matches Android animateCamera)
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
    }

    // Location tracking - matches Android's LaunchedEffect(permissionsState.allPermissionsGranted)
    // Use initialCamera as key so it only runs when initialCamera changes from null to valid or vice versa
    LaunchedEffect(initialCamera?.latitude, initialCamera?.longitude) {
        delay(300) // Match Android's 300ms delay
        // Check if we have a valid initialCamera - if yes, don't start location tracking that could center
        // Check != 0.0 because longitude can be negative (west of prime meridian)
        val hasValidInitialCamera = initialCamera?.latitude.zeroIfNull() != 0.0 && initialCamera?.longitude.zeroIfNull() != 0.0

        println("hasValidInitialCamera. initialCamera?.latitude: ${initialCamera?.latitude}, initialCamera?.longitude: ${initialCamera?.longitude}")
        if (hasValidInitialCamera) {
            // If we have initialCamera, skip location tracking entirely to preserve camera position
            // The marker will be updated only when user explicitly requests current location via goToCurrentLocationTrigger
            println("latitude and longitude > 0 - skipping location tracking to preserve camera position")

            val map = mapViewState.value
            val cam = GMSCameraPosition.cameraWithLatitude(
                initialCamera?.latitude.zeroIfNull(),
                initialCamera?.longitude.zeroIfNull(),
                initialCamera?.zoom.zeroIfNull()
            )
            val update = GMSCameraUpdate.setCamera(cam)
            map?.moveCamera(update)

            return@LaunchedEffect
        } else {
            // Only if no valid initialCamera, center and zoom to location on first load
            println("latitude and longitude <= 0 - will center on first location")

            val locationManager = CLLocationManager()
            // Check authorization status
            val authStatus = locationManager.authorizationStatus
            val isAuthorized = authStatus == kCLAuthorizationStatusAuthorizedWhenInUse || authStatus == kCLAuthorizationStatusAuthorizedAlways

            if (isAuthorized) {
                println("allPermissionsGranted")
                println("allPermissionsGranted. latitude: ${initialCamera?.latitude}, longitude: ${initialCamera?.longitude}")

                class DelegateImpl: NSObject(), CLLocationManagerDelegateProtocol {
                    override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                        val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                        val map = mapViewState.value ?: return

                        // Check again if initialCamera was set while waiting for location
                        val currentInitialCamera = initialCamera
                        val hasValidCamera = currentInitialCamera?.latitude.zeroIfNull() != 0.0 && currentInitialCamera?.longitude.zeroIfNull() != 0.0

                        if (hasValidCamera) {
                            // If initialCamera was set, just update marker without centering
                            last.coordinate.useContents {
                                locationMarkerState.value?.map = null
                            }

                            manager.stopUpdatingLocation()
                            return
                        }

                        last.coordinate.useContents {
                            println("lastLocation: is not null: lat=$latitude, lng=$longitude")
                            // Center and zoom to location (matches Android moveCamera)
                            val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                            val update = GMSCameraUpdate.setCamera(camera)
                            map.moveCamera(update)
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
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val mapView = GMSMapView()
            mapViewState.value = mapView
            mapView.myLocationEnabled = true // Use default marker

            // Disable system icons - matches Android's uiSettings.isMapToolbarEnabled = false
            mapView.settings.myLocationButton = false

            // Load map style (matches Android's grey style)
            // Using embedded JSON string to avoid bundle dependency
            val style = GMSMapStyle.styleWithJSONString(mapStyle(), null)
            if (style != null) {
                mapView.mapStyle = style
            } else {
                println("Failed to load map style from JSON string")
            }

            // Restore initial camera position immediately if available (before map renders)
            // This prevents the visible zoom effect when navigating back to the map - matches Android
            if (initialCamera?.latitude.zeroIfNull() != 0.0 && initialCamera?.longitude.zeroIfNull() != 0.0) {
                println("initialCamera?.latitude: ${initialCamera?.latitude}, initialCamera?.longitude: ${initialCamera?.longitude}")
                val cam = GMSCameraPosition.cameraWithLatitude(
                    initialCamera?.latitude.zeroIfNull(),
                    initialCamera?.longitude.zeroIfNull(),
                    initialCamera?.zoom.zeroIfNull()
                )
                val update = GMSCameraUpdate.setCamera(cam)
                mapView.moveCamera(update)
            }

            // Delegate for camera changes - matches Android's setOnCameraIdleListener
            // Use idleAtCameraPosition which fires when map becomes idle (matches setOnCameraIdleListener)
            class MapDelegate: NSObject(), cocoapods.GoogleMaps.GMSMapViewDelegateProtocol {
                override fun mapView(mapView: GMSMapView, idleAtCameraPosition: GMSCameraPosition) {
                    // Only save if zoom >= 2f - matches Android condition
                    if (idleAtCameraPosition.zoom >= 2.0f) {
                        idleAtCameraPosition.target.useContents {
                            // Check != 0.0 because longitude can be negative (west of prime meridian)
                            println("setOnCameraIdleListener. latitude: ${this.latitude}, longitude: ${this.longitude}")
                            if (this.latitude > 0.0 && this.longitude > 0.0) {
                                onCameraChanged(
                                    MapCameraState(
                                        latitude = this.latitude,
                                        longitude = this.longitude,
                                        zoom = idleAtCameraPosition.zoom,
                                        bearing = idleAtCameraPosition.bearing.toFloat(),
                                        tilt = idleAtCameraPosition.viewingAngle.toFloat()
                                    )
                                )
                            }
                        }
                    }
                }
            }

            val mapDelegate = MapDelegate()
            mapDelegateState.value = mapDelegate
            mapView.delegate = mapDelegate
            mapView
        },

        update = { view ->
            mapViewState.value = view
            // Ensure delegate is set if it was lost
            if (view.delegate == null && mapDelegateState.value != null) {
                view.delegate = mapDelegateState.value
            }

            if (initialCamera?.latitude.zeroIfNull() != 0.0 && initialCamera?.longitude.zeroIfNull() != 0.0) {
                val currentCamera = view.camera
                currentCamera.target.useContents {
                    // Only restore if current camera is different from initialCamera (to avoid unnecessary updates)
                    val targetLat = initialCamera?.latitude.zeroIfNull()
                    val targetLng = initialCamera?.longitude.zeroIfNull()

                    if (currentCamera.zoom < 2.0f) {
                        println("Restoring camera in update block: lat=$targetLat, lng=$targetLng, zoom=${initialCamera?.zoom.zeroIfNull()}")
                        val cam = GMSCameraPosition.cameraWithLatitude(
                            targetLat,
                            targetLng,
                            initialCamera?.zoom.zeroIfNull()
                        )
                        val update = GMSCameraUpdate.setCamera(cam)
                        view.moveCamera(update)
                    }
                }
            }
        }
    )
}
