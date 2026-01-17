package com.example.kvizist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp



/*
@Composable
fun QuizScreen(navController: NavController, sessionData: SessionData) {
    val context = LocalContext.current
    val questions = remember(context) { QuestionLoader.load(context) }

    var currentIndex by remember { mutableStateOf(0) }
    val isLast = questions.isNotEmpty() && currentIndex == questions.lastIndex

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
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
                        if (isLast) {
                            navController.navigate(Routes.HOME)
                        } else {
                            currentIndex++
                        }
                    }
                ) {
                    Text(if (isLast) "Finish" else "Next")
                }
            }
        }
    ) { paddingValues ->

        // Full background
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF087F8C))
                .padding(paddingValues),
            contentAlignment = Alignment.Center // centers the card on screen
        ) {
            questions.getOrNull(currentIndex)?.let { question ->
                // Card container
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .height(600.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    // Column inside card starts from top
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Top, // top start
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        QuestionView(question)
                    }
                }
            }
        }
    }
}
*/

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizScreen(navController: NavController, sessionData: SessionData) {
    val context = LocalContext.current
    val questions = remember(context) { QuestionLoader.load(context) }

    var currentIndex by remember { mutableIntStateOf(0) }
    var previousIndex by remember { mutableIntStateOf(0) }

    val isLast = questions.isNotEmpty() && currentIndex == questions.lastIndex

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        if (currentIndex > 0) {
                            previousIndex = currentIndex
                            currentIndex--
                        }
                    },
                    enabled = currentIndex > 0
                ) {
                    Text("Previous")
                }

                Button(
                    onClick = {
                        previousIndex = currentIndex
                        if (isLast) {
                            navController.navigate(Routes.HOME)
                        } else {
                            currentIndex++
                        }
                    }
                ) {
                    Text(if (isLast) "Finish" else "Next")
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF087F8C))
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            // Shadow card
            Card(
                modifier = Modifier
                    .width(350.dp)
                    .height(600.dp)
                    .offset(x = 16.dp, y = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF002F5B)),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {}

            AnimatedContent(
                targetState = currentIndex,
                transitionSpec = {


                    if (targetState > previousIndex) {
                        // Forward → slide from right off-screen
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> fullWidth },
                            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                        ) + fadeIn(animationSpec = tween(600)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> -fullWidth },
                                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                                ) + fadeOut(animationSpec = tween(600))
                    } else {
                        // Backward → slide from left off-screen
                        slideInHorizontally(
                            initialOffsetX = { fullWidth -> -fullWidth },
                            animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                        ) + fadeIn(animationSpec = tween(600)) togetherWith
                                slideOutHorizontally(
                                    targetOffsetX = { fullWidth -> fullWidth },
                                    animationSpec = tween(durationMillis = 600, easing = FastOutSlowInEasing)
                                ) + fadeOut(animationSpec = tween(600))
                    }
                }
            ) { targetIndex ->
                questions.getOrNull(targetIndex)?.let { question ->
                    Card(
                        modifier = Modifier
                            .width(350.dp)
                            .height(600.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
                        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            QuestionView(question)
                        }
                    }
                }
            }


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

// ---------------- MC Question ----------------
@Composable
fun MCQuestionView(question: MCQuestion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(32.dp))

        question.options.forEach { option ->
            OptionButton(text = option, onClick = { })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ---------------- TF Question ----------------
@Composable
fun TFQuestionView(question: TFQuestion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        // True on top, False below
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OptionButton(text = "True", onClick = { })
            OptionButton(text = "False", onClick = { })
        }
    }
}

// ---------------- Flashcard ----------------
@Composable
fun FlashcardQuestionView(question: FlashcardQuestion) {
    var showAnswer by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (showAnswer) question.answer else question.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))

        OptionButton(
            text = if (showAnswer) "Hide answer" else "Show answer",
            onClick = { showAnswer = !showAnswer }
        )
    }
}

// ---------------- Image Question ----------------
@Composable
fun ImageQuestionView(question: ImageQuestion) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Image URL: ${question.imageUrl}")

        Spacer(modifier = Modifier.height(16.dp))

        question.options.forEach { option ->
            OptionButton(text = option, onClick = { })
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

// ---------------- Option Button ----------------
@Composable
fun OptionButton(text: String, onClick: () -> Unit) {
    ElevatedButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}



