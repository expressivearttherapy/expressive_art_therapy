package com.testing.wellness.screens

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator

class SelectMoodScreen : Screen {

    data class MoodInfo(
        val mood: String,
        val checked: Boolean,
        val link: String = ""
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val checkboxes = remember {
            mutableStateListOf(
                MoodInfo("Happy \uD83D\uDE00", false, "spotify:playlist:37i9dQZF1DX1tW4VlEfDSS"),
                MoodInfo("Sad \uD83D\uDE22", false, "spotify:playlist:37i9dQZF1DX7qK8ma5wgG1"),
                MoodInfo("Depressed \uD83D\uDE2D", false, "spotify:playlist:37i9dQZF1DX3YSRoSdA634"),
                MoodInfo("Angry \uD83D\uDE20", false, "spotify:playlist:37i9dQZF1DX1tW4VlEfDSS"),
                MoodInfo("Excited \uD83D\uDE0D", false, "spotify:playlist:37i9dQZF1DX6VdMW310YC7"),
                MoodInfo("Calm \uD83D\uDE0C", false, "spotify:playlist:37i9dQZF1DX7qK8ma5wgG1"),
                MoodInfo("Tired \uD83D\uDE2B", false, "spotify:playlist:37i9dQZF1DX3YSRoSdA634"),
                MoodInfo("Hurt \uD83D\uDE2D", false, "spotify:playlist:37i9dQZF1DX1tW4VlEfDSS"),
                MoodInfo("Confused \uD83D\uDE15", false, "spotify:playlist:37i9dQZF1DX6VdMW310YC7"),
                MoodInfo("In love \uD83D\uDE18", false, "spotify:playlist:37i9dQZF1DX7qK8ma5wgG1"),
                MoodInfo("Not sure \uD83D\uDE10", false, "spotify:playlist:37i9dQZF1DX3YSRoSdA634"),
                MoodInfo("In love \uD83D\uDE18", false, "spotify:playlist:37i9dQZF1DX1tW4VlEfDSS"),
                MoodInfo("Not sure \uD83D\uDE10", false, "spotify:playlist:37i9dQZF1DX6VdMW310YC7"),
            )
        }

        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Surface(
            modifier = Modifier
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Select your mood") },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                    )
                },
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    LazyColumn {
                        items(checkboxes.size) { index ->
                            ListItem(
                                modifier = Modifier
                                    .clickable {
                                        navigator?.push(PlayerScreen(checkboxes[index].link))
                                    },
                                headlineContent = { Text(text = checkboxes[index].mood) },
                                trailingContent = {
//                                    Checkbox(
//                                        checked = checkboxes[index].checked,
//                                        onCheckedChange = { checked ->
//                                            checkboxes[index] =
//                                                checkboxes[index].copy(checked = checked)
//                                        }
//                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Next"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}