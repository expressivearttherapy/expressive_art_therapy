package com.testing.wellness

import android.app.Application
import com.spotify.android.appremote.api.SpotifyAppRemote

class MyApp : Application() {
    companion object {
        val spotifyClientId = "4405751618694c8ca9a912ad017b0e7c"
        val spotifyRedirectUri = "com.testing.wellness://callback"
        val spotifyRequestCode = 1337
    }
}