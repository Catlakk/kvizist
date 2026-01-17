package com.example.kvizist

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp

@Composable
fun QuizScreen(
    navController: NavController,
    sessionData: SessionData
) {
    val context = LocalContext.current

    val questions = remember(context) {
        QuestionLoader.load(context)
    }

    var currentIndex by remember { mutableStateOf(0) }
    val isLastQuestion = currentIndex == questions.lastIndex

    val currentQuestion = questions.getOrNull(currentIndex)

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        currentQuestion?.let {
            QuestionView(question = it)
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentIndex > 0) currentIndex-- },
                enabled = currentIndex > 0
            ) {
                Text("Previous")
            }

            Button(
                onClick = {
                    if (isLastQuestion) {
                        navController.navigate(Routes.HOME)
                    } else {
                        currentIndex++
                    }
                }
            ) {
                Text(if (isLastQuestion) "Finish" else "Next")
            }

        }
    }
}

@Composable
fun NavHostTestForQuiz(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Text("QuizScreen")
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            navController.navigate(Routes.HOME)
        })
        {
            Text("Go to HomeScreen")
        }
    }

}

@Composable
fun QuestionView(question: Question) {
    when (question) {
        is MCQuestion -> MCQuestionView(question)
        is TFQuestion -> TFQuestionView(question)
        is FlashcardQuestion -> FlashcardQuestionView(question)
        is ImageQuestion -> ImageQuestionView(question)
    }
}

@Composable
fun MCQuestionView(question: MCQuestion) {
    Column {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEachIndexed { index, option ->
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                onClick = {
                    // later: check index == question.correctIndex
                }
            ) {
                Text(option)
            }
        }
    }
}

@Composable
fun TFQuestionView(question: TFQuestion) {
    Column {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { /* true */ }) {
                Text("True")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = { /* false */ }) {
                Text("False")
            }
        }
    }
}

@Composable
fun FlashcardQuestionView(question: FlashcardQuestion) {
    var showAnswer by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = if (showAnswer) question.answer else question.question,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showAnswer = !showAnswer }) {
            Text(if (showAnswer) "Hide answer" else "Show answer")
        }
    }
}

@Composable
fun ImageQuestionView(question: ImageQuestion) {
    Column {
        Text(question.question)

        Spacer(modifier = Modifier.height(16.dp))

        // Later you can use Coil for image loading
        Text("Image URL: ${question.imageUrl}")

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEachIndexed { index, option ->
            Button(onClick = { /* check answer */ }) {
                Text(option)
            }
        }
    }
}
