
package com.example.kvizist.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kvizist.Routes
import com.example.kvizist.SessionData

@Composable
fun StatisticsScreen(navController: NavController, sessionData: SessionData) {
    val correct = sessionData.correctCount
    val incorrect = sessionData.incorrectCount
    val total = correct + incorrect
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Rezultat", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Text("Točno: $correct")
        Text("Netočno: $incorrect")
        Text("Ukupno: $total")
        Spacer(Modifier.height(24.dp))
        Button(onClick = { navController.navigate(Routes.HOME) }) {
            Text("Povratak na početnu")
        }
    }
}
