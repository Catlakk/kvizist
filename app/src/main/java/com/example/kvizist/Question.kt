
package com.example.kvizist

sealed class Question {
    abstract val type: String
    abstract val lesson: Int
    abstract val question: String
}

data class MCQuestion(
    override val type: String,
    override val lesson: Int,
    override val question: String,
    val options: List<String>,
    val correctIndex: Int
) : Question()

data class TFQuestion(
    override val type: String,
    override val lesson: Int,
    override val question: String,
    val correct: Boolean
) : Question()

data class FlashcardQuestion(
    override val type: String,
    override val lesson: Int,
    override val question: String,
    val answer: String
) : Question()

data class ImageQuestion(
    override val type: String,
    override val lesson: Int,
    override val question: String,
    val imageUrl: String,
    val options: List<String>,
    val correctIndex: Int
) : Question()
