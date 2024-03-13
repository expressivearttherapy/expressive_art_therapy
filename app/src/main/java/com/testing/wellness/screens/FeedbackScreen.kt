package com.testing.wellness.screens

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

class FeedbackScreen : Screen {

    data class RatingQuestion(
        val question: String,
        val rating: Float,
    )

    data class BooleanQuestion(
        val question: String,
        val answer: Boolean,
    )

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        val ratingQuestion = remember {
            mutableStateListOf(
                RatingQuestion("Did the song help you relax?", 2.5f),
                RatingQuestion("How much did the song improve your mood?", 2.5f),
            )
        }

        val booleanQuestion = remember {
            mutableStateListOf(
                BooleanQuestion("Did the song resonate with your current emotions?", false),
                BooleanQuestion("Would you listen to this song again when you're feeling similar emotions?", false),
                BooleanQuestion("Did the song evoke any memories or thoughts for you?", false),
                BooleanQuestion("Did the song meet your expectations in terms of relaxation or mood enhancement?", false),
                BooleanQuestion("Did you like the musical instrument played in relevance to your mood?", false),
            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Feedback") },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                ratingQuestion.forEachIndexed { index, question ->
                    Text(text = question.question)
                    Spacer(modifier = Modifier.padding(8.dp))
                    RatingBar(
                        value = question.rating,
                        style = RatingBarStyle.Fill(),
                        stepSize = StepSize.HALF,
                        onValueChange = { ratingQuestion[index] = question.copy(rating = it) },
                        onRatingChanged = {}
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }

                booleanQuestion.forEachIndexed { index, question ->
                    ListItem(
                        headlineContent = { Text(text = question.question) },
                        trailingContent = {
                            Checkbox(
                                checked = question.answer,
                                onCheckedChange = { checked ->
                                    booleanQuestion[index] = question.copy(answer = checked)
                                }
                            )
                        }
                    )
                }

                Button(onClick = {
                    navigator?.push(ThankYouScreen())
                }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}