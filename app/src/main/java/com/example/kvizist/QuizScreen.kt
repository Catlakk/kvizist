package com.example.kvizist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

//
@Composable
fun QuizScreen(navController: NavController) {
    //set up question holder
    var questionHolder = mutableListOf<QuizQuestion>()

    //load questions from local files
    testLoad(questionHolder)

    //start the quiz
    ActiveQuiz(questionHolder, navController)
}

fun testLoad(questionHolder: MutableList<QuizQuestion>)
{
    val tempList = listOf<String>("a", "b", "c")
    for (i in 0..2){
        val q = QuizQuestion(
            i+1,
            "q$i",
            "correct",
            tempList,
            "single",
            1,
            "testSubject",
            2,
        )
        questionHolder.add(q)
    }
    val tempList2 = listOf<String>("a")
    val p = QuizQuestion(
        1,
        "q6",
        "correct",
        tempList2,
        "single",
        1,
        "testSubject",
        2,
    )
    questionHolder.add(p)
}

@Composable
fun ActiveQuiz(
    questionHolder: List<QuizQuestion>,
    navController: NavController
) {
    var position by remember { mutableIntStateOf(0) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            // Display question
            Text(
                text = questionHolder[position].question,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display answers
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                items(questionHolder[position].possibleAnswers.size) { index ->
                    LabeledPrettyCheckbox(
                        text = questionHolder[position].possibleAnswers[index],
                        index = index,
                        question = questionHolder[position]
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Next button
            Button(onClick = {
                if (position >= questionHolder.size - 1) {
                    navController.navigate("home") {
                        popUpTo("activeQuiz") { inclusive = true }
                    }
                } else {
                    position++
                }
            }) {
                Text("Next")
            }
        }
    }
}

@Composable
fun LabeledPrettyCheckbox(
    text: String,
    index: Int,
    question: QuizQuestion
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                question.userAnswerList[index] = !question.userAnswerList[index]
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = question.userAnswerList[index],
            onCheckedChange = null // handled by Row
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}
