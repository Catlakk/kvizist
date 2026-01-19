package com.example.kvizist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/*Todo:
*  1. Next button sam bez previous (edge case error)
*  2. Next Button je lock in button
*  3. Zapamti odgove
*  4. Zapisi tocnost odgovore u sessionData
*  5. Prikazi je su li odgovori tocni ili nisu (Next Button)
* */



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
    //dodaj fun QuizLoad(QuizMode)
    // dodaj QuestionManager da prati question i answers
    val questions = remember(context) { QuestionLoader.load(context) }

    var currentIndex by remember { mutableIntStateOf(0) }
    var previousIndex by remember { mutableIntStateOf(0) }
    var checkAnswer by remember { mutableStateOf(false) }
    var resetButton by remember { mutableStateOf(false) }
    var nextButtonClick by remember { mutableStateOf(false) }
    var lockInState by remember { mutableStateOf(LockInState.CHECK_ANSWER) }

    val isLast = questions.isNotEmpty() && currentIndex == questions.lastIndex

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {


                LockInButton(
                    totalTimeMillis = 8000,
                    onTimeUp = {
                        previousIndex = currentIndex
                        if (isLast) {
                            navController.navigate(Routes.HOME)
                        } else {
                            currentIndex++
                            resetButton = !resetButton
                        }

                    },
                    onClick = {
                        //check answer
                                previousIndex = currentIndex
                                if (isLast) {
                                    navController.navigate(Routes.HOME)
                                } else {
                                    currentIndex++
                                    resetButton = !resetButton
                                }

                    },
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = if (isLast) "Finish" else "Next",
                    reset = resetButton
                )
                /*LockInButton(
                    totalTimeMillis = 8000,
                    onClick = {
                        lockInState = handleLockIn(
                            state = lockInState,
                            isLast = isLast,
                            onCheckAnswer = {
                                checkAnswer = true
                            },
                            onNext = {
                                previousIndex = currentIndex
                                if (isLast) {
                                    navController.navigate(Routes.HOME)
                                } else {
                                    currentIndex++
                                    resetButton = !resetButton
                                    checkAnswer = false
                                }
                            }
                        )
                    },
                    onTimeUp = {
                        lockInState = handleLockIn(
                            state = lockInState,
                            isLast = isLast,
                            onCheckAnswer = {
                                checkAnswer = true
                            },
                            onNext = {
                                previousIndex = currentIndex
                                if (isLast) {
                                    navController.navigate(Routes.HOME)
                                } else {
                                    currentIndex++
                                    resetButton = !resetButton
                                    checkAnswer = false
                                }
                            }
                        )
                    },
                    text = when (lockInState) {
                        LockInState.CHECK_ANSWER -> "Check"
                        LockInState.NEXT_QUESTION -> if (isLast) "Finish" else "Next"
                        LockInState.IDLE -> "Next"
                    },
                    reset = resetButton
                )*/
                /*
                LockInButton(
                    text = when (lockInState) {
                        LockInState.CHECK_ANSWER ->
                            if (isLast) "Finish" else "Next"

                        LockInState.NEXT_QUESTION ->
                            "Check"
                    },
                    onClick = {
                        lockInState = handleLockIn(
                            state = lockInState,
                            onCheckAnswer = {
                                checkAnswer = true
                            },
                            onNext = {
                                previousIndex = currentIndex
                                if (isLast) {
                                    navController.navigate(Routes.HOME)
                                } else {
                                    currentIndex++
                                    resetButton = !resetButton
                                    checkAnswer = false
                                }
                            }
                        )
                    },
                    onTimeUp = { /* same logic */ },
                    reset = resetButton
                )
                */



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
                            QuestionView(question, checkAnswer)
                        }
                    }
                }
            }


        }
    }
}

fun handleLockIn(
    state: LockInState,
    onCheckAnswer: () -> Unit,
    onNext: () -> Unit
): LockInState {
    return when (state) {
        LockInState.CHECK_ANSWER -> {
            onCheckAnswer()
            LockInState.NEXT_QUESTION
        }

        LockInState.NEXT_QUESTION -> {
            onNext()
            LockInState.CHECK_ANSWER
        }
    }
}



@Composable
fun QuestionView(question: Question, checkAnswer: Boolean, ) {
    when (question) {
        is MCQuestion -> MCQuestionView(question, checkAnswer)
        is TFQuestion -> TFQuestionView(question, checkAnswer)
        is FlashcardQuestion -> FlashcardQuestionView(question, checkAnswer)
        is ImageQuestion -> ImageQuestionView(question, checkAnswer)
    }
}

// ---------------- MC Question ----------------
@Composable
fun MCQuestionView(question: MCQuestion, checkAnswer: Boolean) {
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

        question.options.forEachIndexed { index, option ->
            OptionButton(
                text = option,
                optionIndex = index,
                checkAnswer = checkAnswer
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}


// ---------------- TF Question ----------------
@Composable
fun TFQuestionView(question: TFQuestion, checkAnswer: Boolean) {
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

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OptionButton(
                text = "True",
                optionIndex = 0,
                checkAnswer = checkAnswer
            )
            OptionButton(
                text = "False",
                optionIndex = 1,
                checkAnswer = checkAnswer
            )
        }
    }
}


// ---------------- Flashcard ----------------
@Composable
fun FlashcardQuestionView(question: FlashcardQuestion, checkAnswer: Boolean) {
            Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (checkAnswer) question.answer else question.question,
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ---------------- Image Question ----------------
@Composable
fun ImageQuestionView(question: ImageQuestion, checkAnswer: Boolean) {
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

        question.options.forEachIndexed { index, option ->
            OptionButton(
                text = option,
                optionIndex = index,
                checkAnswer = checkAnswer
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun FlashcardButton(text: String, onClick: () -> Unit) {
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
@Composable
fun OptionButton(
    text: String,
    optionIndex: Int,
    checkAnswer: Boolean
) {
    var isSelected by remember { mutableStateOf(false) }
    val selectedColor = Color(30, 144, 255) // Dodger Blue
    val unselectedColor = Color.White

    ElevatedButton(
        onClick = { isSelected = !isSelected },
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = if (isSelected) selectedColor else unselectedColor
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Black
        )
    }
}

@Composable
fun LockInButton(
    text: String,
    modifier: Modifier = Modifier,
    totalTimeMillis: Int = 5000,
    onTimeUp: () -> Unit = {},
    onClick: () -> Unit = {},
    reset: Boolean
) {
    var locked by remember { mutableStateOf(false) }
    val progress = remember { Animatable(0f) }

    // 🔑 Timer resets ONLY when reset changes
    LaunchedEffect(reset) {
        locked = false
        progress.snapTo(0f)

        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = totalTimeMillis,
                easing = LinearEasing
            )
        )

        if (!locked) {
            locked = true
            onTimeUp()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
    ) {
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.value)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color(0xFF1E90FF), Color(0xFF00BFFF))
                    )
                )
        )

        Button(
            onClick = {
                if (!locked) {
                    locked = true
                    onClick()
                }
            },
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = text, color = Color.White)
        }
    }
}

/*
@Composable
fun LockInButton(
    text: String,
    modifier: Modifier = Modifier,
    totalTimeMillis: Int = 5000, // total timer duration
    onTimeUp: () -> Unit = {},
    onClick: () -> Unit = {},
    reset: Boolean
) {
    var isLockedIn by remember { mutableStateOf(true) }
    // Animation progress from 0f -> 1f over totalTimeMillis
    var progress = remember { Animatable(0f) }

    if(reset){
        isLockedIn = true
        progress = Animatable(0f)
    }

    LaunchedEffect(isLockedIn) {
        if (isLockedIn) {
            progress.snapTo(0f)
            progress.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = totalTimeMillis, easing = LinearEasing)
            )
            onTimeUp()
            isLockedIn = false
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
    ) {
        // Gradient fill representing timer
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(progress.value)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF1E90FF), Color(0xFF00BFFF))
                    )
                )
        )

        // Button clickable overlay
        Button(
            onClick = { isLockedIn = true; onClick() },
            modifier = Modifier
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = text,
                color = Color.White
            )
        }
    }
}

*/

// flashcard show answer button
//fun check answer






