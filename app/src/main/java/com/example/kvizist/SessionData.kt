package com.example.kvizist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SessionData : ViewModel(){
    var accountInfo by mutableStateOf<AccountInfo?>(null)
    var selectedSubject by mutableStateOf<String?>(null)
    var quizMode by mutableStateOf<QuizMode?>(null)
    var quizManager by mutableStateOf<QuizManager?>(null)
}