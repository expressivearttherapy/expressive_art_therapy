package com.testing.wellness.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.spotify.android.appremote.api.ConnectionParams
import com.spotify.android.appremote.api.Connector
import com.spotify.android.appremote.api.SpotifyAppRemote
import com.testing.wellness.MyApp
import com.testing.wellness.R
import kotlin.time.Duration.Companion.days


data class PlayerScreen(val link: String = "") : Screen {
    var spotifyAppRemote: SpotifyAppRemote? = null

    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.current

        var isPlaying by remember { mutableStateOf(false) }
        var trackName by remember { mutableStateOf("") }
        var artistName by remember { mutableStateOf("") }

        LaunchedEffect(key1 = true) {
            val connectionParams = ConnectionParams.Builder(MyApp.spotifyClientId)
                .setRedirectUri(MyApp.spotifyRedirectUri)
                .showAuthView(true)
                .build()

            SpotifyAppRemote.connect(context, connectionParams, object : Connector.ConnectionListener {
                override fun onConnected(appRemote: SpotifyAppRemote) {
                    println("Connected to spotify")
                    appRemote.playerApi.play(link)
                    spotifyAppRemote = appRemote
                    isPlaying = true

                    appRemote.playerApi.subscribeToPlayerState()
                        ?.setEventCallback { playerState ->
                            println("Player state: ${playerState.track.name} by ${playerState.track.artist}")
                            trackName = playerState.track.name
                            artistName = playerState.track.artist.name
                        }
                        ?.setErrorCallback { throwable ->
                            println("Error: ${throwable.message}")
                        }
                }

                override fun onFailure(throwable: Throwable) {
                    println("Failed to connect to spotify: ${throwable.message}")
                }
            })
        }

        Scaffold { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Now playing")

                Spacer(Modifier.height(8.dp))

                Text("Track: $trackName")

                Spacer(Modifier.height(8.dp))

                Text("Artist: $artistName")

                Spacer(Modifier.height(16.dp))

                IconButton(onClick = {
                    isPlaying = if(isPlaying) {
                        spotifyAppRemote?.playerApi?.pause()
                        false
                    } else {
                        spotifyAppRemote?.playerApi?.resume()
                        true
                    }
                }) {
                    if(isPlaying) {
                        Icon(painter = painterResource(R.drawable.baseline_pause_24), contentDescription = "Pause")
                    } else {
                        Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play")
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(onClick = {
                    spotifyAppRemote?.playerApi?.pause()
                    navigator?.push(FeedbackScreen())
                }) {
                    Text(text = "Finish")
                }
            }
        }
    }
}