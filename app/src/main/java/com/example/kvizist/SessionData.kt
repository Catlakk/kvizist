
package com.example.kvizist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SessionData : ViewModel() {
    var selectedSubject by mutableStateOf<String?>(null)
    var correctCount by mutableIntStateOf(0)
    var incorrectCount by mutableIntStateOf(0)
}

