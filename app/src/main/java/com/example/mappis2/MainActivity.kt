package com.example.mappis2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.mappis2.MapScreen

import com.mapmyindia.sdk.maps.MapmyIndia
import com.mmi.services.account.MapmyIndiaAccountManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize MapmyIndia SDK keys
//        MapmyIndiaAccountManager.getInstance().restAPIKey = "854631380194cc21cbee316a04fcbe52"
//        MapmyIndiaAccountManager.getInstance().mapSDKKey = "854631380194cc21cbee316a04fcbe52"
//        MapmyIndiaAccountManager.getInstance().atlasClientId =
//            "96dHZVzsAut9YlYXrycwtlTe7WEgF9j4c_5V7x2a_2mgj2QTMAfAICK860fet60WUFmqWKIlhY2GUX8MRd47cA=="
//        MapmyIndiaAccountManager.getInstance().atlasClientSecret =
//            "lrFxI-iSEg9_YQMjuLDSi1avR_m1Fw0NoOd-LMVCSNiyAxFMoYXdQ1XhX0DaPnYY7WrrB5e19dRNpqVSMXj9NUeIABCx6wVI"
//        MapmyIndia.getInstance(applicationContext)


//        enableEdgeToEdge()
        setContent {

            Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
                Column(modifier = Modifier.padding(padding)) {
                    test()
//                    MapScreen()
                }
            }

        }
    }
}
