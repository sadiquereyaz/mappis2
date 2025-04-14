package com.example.mappis2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DoubleState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

@Composable
fun test(modifier: Modifier = Modifier) {
    val showLocation = remember{
        mutableStateOf(false)
    }
    if(!showLocation.value){
        RequestLocationPermission{
            showLocation.value = true
        }
    }else{
        LocationScreen(context = LocalContext.current)
    }
}
@Composable
fun RequestLocationPermission(onPermissionGranted:()->Unit) {
    val context = LocalContext.current
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            isGranted->
            if(isGranted){
                onPermissionGranted();
            }
        }
    )

    LaunchedEffect(Unit) {
        if(
            ContextCompat.checkSelfPermission(
                context,Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            permissionLauncher.launch(
              Manifest.permission.ACCESS_FINE_LOCATION
            )
        }else{
            onPermissionGranted();
        }
    }
}

@Composable
fun LocationScreen(context: Context) {
    val locationClient = remember {  LocationServices.getFusedLocationProviderClient(context) }
    val locationState = remember { mutableStateOf<Pair<Double,Double>?>(null) }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_DENIED

        if(hasPermission){
            locationClient.lastLocation.addOnSuccessListener {
                location->
                if(location != null){
                    locationState.value == Pair(location.latitude,location.longitude)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${locationState.value}"
        )
    }
}