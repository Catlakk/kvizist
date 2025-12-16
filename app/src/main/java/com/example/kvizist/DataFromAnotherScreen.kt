package com.example.kvizist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class DataFromAnotherScreen : ViewModel(){
    var messageFromHome by mutableStateOf<QuizMode?>(null)
    var messageFromQuiz by mutableStateOf<Question?>(null)
}