package com.example.kvizist

import androidx.compose.runtime.mutableStateListOf

class QuizQuestion(
    val index: Int, //redni broj pitanja
    val question: String,
    var correctAnswer: String,
    var possibleAnswers: List<String>,
    val questionType: String, //single, multiple, flashcard, connect
    var points: Int,
    val subjectName: String,
    val lectureNumber: Int,
    var score: Int = 0, //sets score trough checkAnswer()
) {
    var userAnswerList = mutableStateListOf<Boolean>().apply {
        repeat(possibleAnswers.size) { add(false) }
    }
    /**
     *
     *  fun checkAnswer(userAnswer: List<String>) {
     *         val correctAnswers = correctAnswer.split(" ")
     *         score = userAnswer.count { it in correctAnswers }
     *     }
     *
    fun checkAnswer(userAnswer: List<String>)
    {
        var temp = 0
        for (i in userAnswer)
            if (i in correctAnswer) temp++
            else temp--
        score = temp
    }

    fun checkAnswer(userAnswer: String) {
        when (questionType) {
            "single" -> if (userAnswer == correctAnswer) score = points
            "multiple" -> checkAnswer(userAnswer.split(" "))
            // "flashcard" -> checkFreeInput(userAnswer)
            "connect" -> println("pass to the function Pair<String, Int>")
            else -> println("error")
        }
    }
    **/

}
