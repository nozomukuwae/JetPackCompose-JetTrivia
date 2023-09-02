package com.example.jettrivia.screens

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TriviaHome(viewModel: TriviaViewModel = viewModel()) {
    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        Text(
            text = "Hello android",
            modifier = Modifier.fillMaxSize(),
        )
    }

    Log.d("TriviaHome", viewModel.data.toString())
    val questions = viewModel.data.value.data
    if (questions != null) {
        questions.forEach {
            Log.d("TriviaHome", it.question)
        }
    }
}
