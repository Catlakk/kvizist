package com.example.kvizist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ModuleScreen(navController: NavController, viewModel: DataFromAnotherScreen) {
    //Mode quiz
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        ButtonFlowRow()
        }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){
        Button(onClick = {
            val questionTypes = listOf<String>("single")
            val quizMode = QuizMode(
                5, // treba pazit na max number of questions
                questionTypes
            )
            viewModel.messageFromHome = quizMode
            navController.navigate("quiz") })
        {
            Text("Take a Quiz")
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ButtonFlowRow() {
    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp),
        verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
    ) {
        val counter = 20
        for (index in 1..counter){
            Button(onClick = {}) {
                Text("Button $index")
            }
        }
    }
}