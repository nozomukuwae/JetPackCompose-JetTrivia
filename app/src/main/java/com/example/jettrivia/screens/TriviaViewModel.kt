package com.example.jettrivia.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.Question
import com.example.jettrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TriviaViewModel @Inject constructor(private val questionRepository: QuestionRepository) :
    ViewModel() {
    val data: MutableState<DataOrException<Question, Boolean, Exception>> =
        mutableStateOf(DataOrException<Question, Boolean, Exception>())
    val currentIndex = mutableStateOf(0)
    val selectedChoiceIndex = mutableStateOf<Int?>(null)

    init {
        fetchAllQuestions()
    }

    private fun fetchAllQuestions() {
        viewModelScope.launch {
            data.value = questionRepository.getAllQuestions()
        }
    }
}
