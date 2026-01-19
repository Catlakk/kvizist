
package com.example.kvizist
/*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun ParsingCheckScreen() {
    val ctx = LocalContext.current
    var result by remember { mutableStateOf("Press button") }
    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues) // <-- respects status/nav bars
                .padding(16.dp)
        ) {
            Button(onClick = {
                runCatching {
                    questions = QuestionLoader.load(ctx)
                }.onSuccess {
                    result = "✅ Parsed ${questions.size} questions correctly"
                }.onFailure {
                    result = "❌ ERROR: ${it.message}"
                }
            }) {
                Text("Parse quiz.json")
            }

            Spacer(Modifier.height(16.dp))
            Text(result)

            Spacer(Modifier.height(16.dp))
            questions.forEach {
                Text("${it.type} | lesson ${it.lesson} | ${it.question}")
            }
        }
    }
}
*/