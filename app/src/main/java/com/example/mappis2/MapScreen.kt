package com.example.mappis2


import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.mapmyindia.sdk.maps.MapView
import com.mapmyindia.sdk.maps.MapmyIndiaMap
import com.mapmyindia.sdk.maps.OnMapReadyCallback
import com.mapmyindia.sdk.maps.annotations.MarkerOptions
import com.mapmyindia.sdk.maps.camera.CameraUpdateFactory
import com.mapmyindia.sdk.maps.geometry.LatLng

@Composable
fun MapScreen(longitude: Double, latitude: Double) {
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val mapView = remember { MapView(context).apply { onCreate(null) } }

    // Manage lifecycle of mapView
    DisposableEffect(lifecycle) {
        val observer = object : DefaultLifecycleObserver {
            override fun onStart(owner: LifecycleOwner) = mapView.onStart()
            override fun onResume(owner: LifecycleOwner) = mapView.onResume()
            override fun onPause(owner: LifecycleOwner) = mapView.onPause()
            override fun onStop(owner: LifecycleOwner) = mapView.onStop()
            override fun onDestroy(owner: LifecycleOwner) = mapView.onDestroy()
        }

        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = Modifier.fillMaxSize()
    )

    // run whenever compose recompose
    SideEffect {
        mapView.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(mapmyIndiaMap: MapmyIndiaMap) {
                Log.d("MAPSCREEN", "onMapReady called")

                Log.d("MAPSCREEN", "latitude: $latitude, longitude: $longitude")
                val location = LatLng(latitude, longitude)
//                val location = LatLng(28.5643748,77.2825257)    // hostel coordinates

                // Add marker
                val markerOptions = MarkerOptions()
                    .position(location)
                    .title("Marker")
                    .snippet("This is a Marker")

                mapmyIndiaMap.addMarker(markerOptions)

                // Center the camera
                mapmyIndiaMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 14.0))
            }

            override fun onMapError(p0: Int, p1: String?) {
                Log.e("MapError", "Map error: $p0 - $p1")
            }
        })
    }
}
