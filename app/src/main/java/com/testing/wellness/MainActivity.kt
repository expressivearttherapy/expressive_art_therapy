package com.testing.wellness

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import cafe.adriel.voyager.transitions.SlideTransition
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.testing.wellness.screens.FeedbackScreen
import com.testing.wellness.screens.PlayerScreen
import com.testing.wellness.screens.WelcomeScreen
import com.testing.wellness.ui.theme.WellnessTheme

class MainActivity : ComponentActivity() {
    override fun onStart() {
        super.onStart()

        val builder = AuthorizationRequest
            .Builder(MyApp.spotifyClientId, AuthorizationResponse.Type.TOKEN, MyApp.spotifyRedirectUri);

        builder.setScopes(arrayOf("streaming"))

        val request = builder.build()

        AuthorizationClient.openLoginActivity(this, MyApp.spotifyRequestCode, request)

//        val connectionParams = ConnectionParams.Builder(MyApp.spotifyClientId)
//            .setRedirectUri(MyApp.spotifyRedirectUri)
//            .showAuthView(false)
//            .build()
//
//        SpotifyAppRemote.connect(this, connectionParams, object : Connector.ConnectionListener {
//            override fun onConnected(appRemote: SpotifyAppRemote) {
//                println("Connected to spotify")
//            }
//
//            override fun onFailure(throwable: Throwable) {
//                println("Failed to connect to spotify: ${throwable.message}")
//            }
//        })
    }

//    @Deprecated("Deprecated in Java")
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == spotifyRequestCode) {
//            val response = AuthorizationClient.getResponse(resultCode, data)
//            if (response.type == AuthorizationResponse.Type.TOKEN) {
//                println("Got token: ${response.accessToken}")
//            }
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WellnessTheme {
                Navigator(WelcomeScreen()) { SlideTransition(it) }
            }
        }
    }
}