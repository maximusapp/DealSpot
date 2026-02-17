@file:OptIn(ExperimentalForeignApi::class)

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
import com.app.dealspot.domain.model.LatLngEntity
import com.app.dealspot.presentation.utils.mapStyle
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.readValue
import kotlinx.cinterop.useContents
import kotlinx.coroutines.delay
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.darwin.NSObject

@Composable
actual fun SelectableMap(
    modifier: Modifier,
    selectedPosition: LatLngEntity?,
    onMapClick: (LatLngEntity) -> Unit,
    onLocationAvailable: (LatLngEntity) -> Unit
) {
    val mapViewState = remember { mutableStateOf<GMSMapView?>(null) }
    val locationMarkerState = remember { mutableStateOf<GMSMarker?>(null) }
    val mapDelegateState = remember { mutableStateOf<cocoapods.GoogleMaps.GMSMapViewDelegateProtocol?>(null) }

    fun updateSelectionMarker(mapView: GMSMapView?, latitude: Double, longitude: Double) {
        var map = mapView ?: return
        val marker = locationMarkerState.value ?: GMSMarker().apply {
            map = mapView
            locationMarkerState.value = this
        }
        memScoped {
            val coordinate = alloc<cocoapods.GoogleMaps.CLLocationCoordinate2D>()
            coordinate.let {
                it.latitude = latitude
                it.longitude = longitude
            }
            marker.position = coordinate.readValue()
        }
        marker.map = map
    }

    LaunchedEffect(Unit) {
        val mapView = mapViewState.value
        if (mapView != null) {
            val locationManager = CLLocationManager()

            class DelegateImpl : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    val map = mapViewState.value ?: return

                    last.coordinate.useContents {
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        map.animateWithCameraUpdate(update)
                        updateSelectionMarker(map, latitude, longitude)
                        onLocationAvailable(LatLngEntity(latitude = latitude, longitude = longitude))
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

    LaunchedEffect(Unit) {
        delay(300)
        val locationManager = CLLocationManager()
        val authStatus = locationManager.authorizationStatus
        val isAuthorized = authStatus == kCLAuthorizationStatusAuthorizedWhenInUse || authStatus == kCLAuthorizationStatusAuthorizedAlways

        if (isAuthorized) {
            class DelegateImpl : NSObject(), CLLocationManagerDelegateProtocol {
                override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
                    val last = (didUpdateLocations.lastOrNull() as? CLLocation) ?: return
                    val map = mapViewState.value ?: return

                    last.coordinate.useContents {
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        map.moveCamera(update)
                        updateSelectionMarker(map, latitude, longitude)
                        onLocationAvailable(LatLngEntity(latitude = latitude, longitude = longitude))
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
            mapView.myLocationEnabled = true
            mapView.settings.myLocationButton = true // Enable "My Location" button

            val style = GMSMapStyle.styleWithJSONString(mapStyle(), null)
            if (style != null) {
                mapView.mapStyle = style
            }

            class MapDelegate : NSObject(), cocoapods.GoogleMaps.GMSMapViewDelegateProtocol {
                override fun mapView(mapView: GMSMapView, idleAtCameraPosition: GMSCameraPosition) {
                    // Handle camera idle events if needed
                }

                @ObjCSignatureOverride
                override fun mapView(mapView: GMSMapView, didTapAtCoordinate: kotlinx.cinterop.CValue<cocoapods.GoogleMaps.CLLocationCoordinate2D>) {
                    // Handle direct map tap - equivalent to Android's setOnMapClickListener
                    didTapAtCoordinate.useContents {
                        updateSelectionMarker(mapView, latitude, longitude)
                        onMapClick(LatLngEntity(latitude = latitude, longitude = longitude))
                        val currentZoom = mapView.camera.zoom
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, currentZoom)
                        val update = GMSCameraUpdate.setCamera(camera)
                        mapView.animateWithCameraUpdate(update)
                    }
                }

                override fun didTapMyLocationButtonForMapView(mapView: GMSMapView): Boolean {
                    // Return false to allow default behavior, then handle in didTapMyLocation
                    return false
                }

                @ObjCSignatureOverride
                override fun mapView(mapView: GMSMapView, didTapMyLocation: kotlinx.cinterop.CValue<cocoapods.GoogleMaps.CLLocationCoordinate2D>) {
                    // Handle "My Location" button tap - move camera to current location
                    didTapMyLocation.useContents {
                        // Animate camera to current location
                        val camera = GMSCameraPosition.cameraWithLatitude(latitude, longitude, 14.0f)
                        val update = GMSCameraUpdate.setCamera(camera)
                        mapView.animateWithCameraUpdate(update)
                        updateSelectionMarker(mapView, latitude, longitude)
                        onLocationAvailable(LatLngEntity(latitude = latitude, longitude = longitude))
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
            if (view.delegate == null && mapDelegateState.value != null) {
                view.delegate = mapDelegateState.value
            }
        }
    )

    LaunchedEffect(selectedPosition?.latitude, selectedPosition?.longitude) {
        val mapView = mapViewState.value
        val position = selectedPosition
        if (mapView != null && position != null) {
            updateSelectionMarker(mapView, position.latitude, position.longitude)
        }
    }
}


