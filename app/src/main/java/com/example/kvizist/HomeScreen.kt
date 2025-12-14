package com.example.kvizist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController, viewModel: dataFromAnotherScreen) {
    Column {
        if (viewModel.messageFromQuiz.isNotEmpty()) {
            Text("Message from Quiz: ${viewModel.messageFromQuiz}")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            viewModel.messageFromHome = "bbbbbbbbbbbbbbbbbb"
            navController.navigate("quiz") })
        {
        Text("Take a Quiz")
    }}
}