package com.example.kvizist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class dataFromAnotherScreen : ViewModel(){
    var messageFromHome by mutableStateOf("")
    var messageFromQuiz by mutableStateOf("")
}