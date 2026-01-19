
package com.example.kvizist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun ModeScreen(navController: NavController, sessionData: SessionData) {
    Scaffold(
        floatingActionButton = {
            Button(onClick = { navController.navigate(Routes.QUIZ) }) { Text("Start Quiz") }
        }
    ) { _ ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { Text(sessionData.selectedSubject ?: "") }
    }
}
