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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

//
@Composable
fun QuizScreen(navController: NavController,  viewModel: dataFromAnotherScreen) {
    //set up question holder
    val questionHolder = mutableListOf<QuizQuestion>()

    //load questions from local files
    testLoad(questionHolder, viewModel.messageFromHome!!) //hardwired, just for testing

    //start the quiz
    ActiveQuiz(questionHolder, navController, viewModel)
}

fun testLoad(questionHolder: MutableList<QuizQuestion>, quizMode: QuizMode)
{
    //sort files to load by subject and lecture
    //sort files to load by quiz mode

    for (i in  1..quizMode.numberOfQuestions){

        questionHolder.add(QuizQuestion(i,
            "q$i",
            "correct",
            listOf<String>("a", "b", "c", "d"),
            "single",
            1,
            "testSubject",
            2,
        ))
    }
}

@Composable
fun ActiveQuiz(
    questionHolder: List<QuizQuestion>,
    navController: NavController,
    viewModel: dataFromAnotherScreen
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
                    //viewModel.messageFromQuiz = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
                    navController.navigate("home") {
                        popUpTo("activeQuiz") { inclusive = true }
                    }
                } else {
                    position++
                }
            }) {
                if (position >= questionHolder.size - 1)
                    Text("End")
                else
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
