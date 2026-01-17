
package com.example.kvizist

import android.content.Context
import com.example.kvizist.FlashcardQuestion
import com.example.kvizist.ImageQuestion
import com.example.kvizist.MCQuestion
import com.example.kvizist.Question
import com.example.kvizist.TFQuestion
import org.json.JSONArray

object QuestionLoader {

    fun load(context: Context): List<Question> {
        val text = context.assets.open("quiz.json")
            .bufferedReader()
            .use { it.readText() }

        val json = JSONArray(text)
        val out = mutableListOf<Question>()

        for (i in 0 until json.length()) {
            val obj = json.getJSONObject(i)
            val type = obj.getString("type")

            when (type) {
                "MC" -> {
                    val optionsArray = obj.getJSONArray("options")
                    val options = List(optionsArray.length()) { idx ->
                        optionsArray.getString(idx)
                    }
                    out.add(
                        MCQuestion(
                            type = type,
                            lesson = obj.getInt("lesson"),
                            question = obj.getString("question"),
                            options = options,
                            correctIndex = obj.getInt("correctIndex")
                        )
                    )
                }

                "TF" -> {
                    out.add(
                        TFQuestion(
                            type = type,
                            lesson = obj.getInt("lesson"),
                            question = obj.getString("question"),
                            correct = obj.getBoolean("correct")
                        )
                    )
                }

                "FLASHCARD" -> {
                    out.add(
                        FlashcardQuestion(
                            type = type,
                            lesson = obj.getInt("lesson"),
                            question = obj.getString("question"),
                            answer = obj.getString("answer")
                        )
                    )
                }

                "IMAGE" -> {
                    val optionsArray = obj.getJSONArray("options")
                    val options = List(optionsArray.length()) { idx ->
                        optionsArray.getString(idx)
                    }
                    out.add(
                        ImageQuestion(
                            type = type,
                            lesson = obj.getInt("lesson"),
                            question = obj.getString("question"),
                            imageUrl = obj.getString("imageUrl"),
                            options = options,
                            correctIndex = obj.getInt("correctIndex")
                        )
                    )
                }

                else -> {
                    // Unknown type: skip or throw
                    // throw IllegalArgumentException("Unknown question type: $type")
                }
            }
        }
        return out
    }
}
