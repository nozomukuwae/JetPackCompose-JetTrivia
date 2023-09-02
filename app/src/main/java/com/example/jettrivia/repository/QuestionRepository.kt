package com.example.jettrivia.repository

import android.util.Log
import com.example.jettrivia.data.DataOrException
import com.example.jettrivia.model.Question
import com.example.jettrivia.network.QuestionApi
import java.lang.Exception
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionApi: QuestionApi) {
    suspend fun getAllQuestions(): DataOrException<Question, Boolean, Exception> {
        val dataOrException = DataOrException<Question, Boolean, Exception>()
        try {
            dataOrException.loading = true
            dataOrException.data = questionApi.getAllQuestions()
        } catch (exception: Exception) {
            dataOrException.e = exception
            Log.d("QuestionRepository", "getAllQuestions: ${exception.localizedMessage}")
        } finally {
            dataOrException.loading = false
        }

        return dataOrException
    }
}
