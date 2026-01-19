
package com.example.kvizist.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kvizist.FlashcardQuestion
import com.example.kvizist.MCQuestion
import com.example.kvizist.QuestionLoader
import com.example.kvizist.Routes
import com.example.kvizist.SessionData
import com.example.kvizist.TFQuestion
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuizScreen(navController: NavController, sessionData: SessionData) {
    val context = LocalContext.current
    val questions = remember(context) { QuestionLoader.load(context) }

    var currentIndex by remember { mutableIntStateOf(0) }
    var previousIndex by remember { mutableIntStateOf(0) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var locked by remember { mutableStateOf(false) }
    var resetKey by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val revealDelayMillis = 900

    LaunchedEffect(Unit) {
        sessionData.correctCount = 0
        sessionData.incorrectCount = 0
    }

    val isLast = questions.isNotEmpty() && currentIndex == questions.lastIndex
    val current = questions.getOrNull(currentIndex)

    fun evaluateIfNeeded() {
        if (locked) return
        when (val q = current) {
            is MCQuestion -> {
                val ok = selectedIndex != null && selectedIndex == q.correctIndex
                if (ok) sessionData.correctCount++ else sessionData.incorrectCount++
            }
            is TFQuestion -> {
                val choice = when (selectedIndex) { 0 -> true; 1 -> false; else -> null }
                val ok = choice != null && choice == q.correct
                if (ok) sessionData.correctCount++ else sessionData.incorrectCount++
            }
            is FlashcardQuestion -> {}
            else -> {}
        }
        locked = true
    }

    fun resetForNext() {
        selectedIndex = null
        locked = false
        resetKey = !resetKey
    }

    fun goNext() {
        previousIndex = currentIndex
        if (isLast) {
            navController.navigate(Routes.STATISTICS)
        } else {
            currentIndex++
            resetForNext()
        }
    }

    Scaffold(
        bottomBar = {
            if (current !is FlashcardQuestion) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LockInButton(
                        text = if (!locked) "Next" else if (isLast) "Finish" else "Next",
                        totalTimeMillis = 8000,
                        onTimeUp = {
                            if (!locked) {
                                evaluateIfNeeded()
                                scope.launch {
                                    delay(revealDelayMillis.toLong())
                                    goNext()
                                }
                            }
                        },
                        onClick = {
                            if (!locked) {
                                evaluateIfNeeded()
                            } else {
                                scope.launch { goNext() }
                            }
                        },
                        reset = resetKey,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    )
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
                        slideInHorizontally(initialOffsetX = { it }) + fadeIn(tween(600)) togetherWith
                                slideOutHorizontally(targetOffsetX = { -it }) + fadeOut(tween(600))
                    } else {
                        slideInHorizontally(initialOffsetX = { -it }) + fadeIn(tween(600)) togetherWith
                                slideOutHorizontally(targetOffsetX = { it }) + fadeOut(tween(600))
                    }
                }
            ) { target ->
                val q = questions.getOrNull(target)
                Card(
                    modifier = Modifier
                        .width(350.dp)
                        .height(600.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        when (q) {
                            is MCQuestion -> {
                                Text(
                                    text = q.question,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(Modifier.height(32.dp))
                                q.options.forEachIndexed { idx, opt ->
                                    val bg = when {
                                        !locked && selectedIndex == idx -> Color(0xFF1E90FF)
                                        locked && idx == q.correctIndex -> Color(0xFF2E9F8F)
                                        locked && selectedIndex == idx && idx != q.correctIndex -> Color(0xFFB00020)
                                        else -> Color.White
                                    }
                                    ElevatedButton(
                                        onClick = { if (!locked) selectedIndex = idx },
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = bg
                                        ),
                                        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                                    ) {
                                        Text(
                                            text = opt,
                                            color = if (bg == Color.White) Color.Black else Color.White
                                        )
                                    }
                                    Spacer(Modifier.height(12.dp))
                                }
                            }
                            is TFQuestion -> {
                                Text(
                                    text = q.question,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(Modifier.height(24.dp))
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    listOf("True" to 0, "False" to 1).forEach { (label, idx) ->
                                        val isCorrectIdx = if (q.correct) 0 else 1
                                        val bg = when {
                                            !locked && selectedIndex == idx -> Color(0xFF1E90FF)
                                            locked && idx == isCorrectIdx -> Color(0xFF2E9F8F)
                                            locked && selectedIndex == idx && idx != isCorrectIdx -> Color(0xFFB00020)
                                            else -> Color.White
                                        }
                                        ElevatedButton(
                                            onClick = { if (!locked) selectedIndex = idx },
                                            modifier = Modifier
                                                .fillMaxWidth(0.9f)
                                                .height(56.dp),
                                            shape = RoundedCornerShape(16.dp),
                                            colors = ButtonDefaults.elevatedButtonColors(
                                                containerColor = bg
                                            ),
                                            elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                                        ) {
                                            Text(
                                                text = label,
                                                color = if (bg == Color.White) Color.Black else Color.White
                                            )
                                        }
                                    }
                                }
                            }
                            is FlashcardQuestion -> {
                                var show by remember(currentIndex) { mutableStateOf(false) }
                                Text(
                                    text = if (show) q.answer else q.question,
                                    style = MaterialTheme.typography.headlineMedium,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(Modifier.height(24.dp))
                                ElevatedButton(
                                    onClick = { show = !show },
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .height(56.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                                ) {
                                    Text(if (show) "Hide answer" else "Show answer")
                                }
                                Spacer(Modifier.height(16.dp))
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    ElevatedButton(
                                        onClick = {
                                            sessionData.correctCount++
                                            goNext()
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = Color(0xFF2E9F8F)
                                        ),
                                        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                                    ) { Text("Točno", color = Color.White) }
                                    ElevatedButton(
                                        onClick = {
                                            sessionData.incorrectCount++
                                            goNext()
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(56.dp),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = ButtonDefaults.elevatedButtonColors(
                                            containerColor = Color(0xFFB00020)
                                        ),
                                        elevation = ButtonDefaults.elevatedButtonElevation(6.dp)
                                    ) { Text("Netočno", color = Color.White) }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LockInButton(
    text: String,
    modifier: Modifier = Modifier,
    totalTimeMillis: Int = 5000,
    onTimeUp: () -> Unit = {},
    onClick: () -> Unit = {},
    reset: Boolean
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(reset) {
        progress.snapTo(0f)
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = totalTimeMillis, easing = LinearEasing)
        )
        onTimeUp()
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color.Black)
    ) {
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
            onClick = { onClick() },
            modifier = Modifier.fillMaxSize(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = text, color = Color.White)
        }
    }
}
