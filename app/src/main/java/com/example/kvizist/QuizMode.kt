package com.example.kvizist

class QuizMode(
    val numberOfQuestions: Int,
    val questionTypes: List<String>, //single, multiple, flashcard, connect
    val timer: Int = -1,
){

}