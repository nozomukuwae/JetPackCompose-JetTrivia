package com.example.jettrivia.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TriviaHome(viewModel: TriviaViewModel = viewModel()) {
    val questions = viewModel.data.value.data

    if (questions != null) {
        if (viewModel.currentIndex.value < questions.size) {
            QuestionScreen(viewModel) {
                // Check answer
                viewModel.selectedChoiceIndex.value = null
                viewModel.currentIndex.value++
            }
        } else {
            // Result screen
        }
    } else if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        Log.d("TriviaHome", viewModel.data.value.e?.localizedMessage ?: "Unexpected error")
    }
}

@Composable
fun QuestionScreen(
    viewModel: TriviaViewModel,
    onNext: () -> Unit,
) {
    val questions = viewModel.data.value.data!!
    val currentQuestion = questions[viewModel.currentIndex.value]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = "Question ${viewModel.currentIndex.value + 1}/${questions.size}",
        )
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Text(
            text = currentQuestion.question,
        )
        Spacer(modifier = Modifier.height(200.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(count = currentQuestion.choices.size) {
                AnswerChoice(
                    text = currentQuestion.choices[it],
                    selected = viewModel.selectedChoiceIndex.value == it,
                ) {
                    viewModel.selectedChoiceIndex.value = it
                }
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = onNext,
                shape = CircleShape,
            ) {
                Text(
                    if (viewModel.currentIndex.value < questions.size) {
                        "Next"
                    } else {
                        "Show result"
                    },
                )
            }
        }
    }
}

@Composable
fun AnswerChoice(text: String, selected: Boolean, onSelect: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 4.dp, Color.Blue),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = onSelect,
            )
            Text(text)
        }
    }
}
