package com.example.kvizist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun ModeScreen(navController: NavController, sessionData: SessionData){
    NavHostTestForMode(navController)

    //sessionData test:
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center)
    {Text("${sessionData.selectedSubject}") }
}


@Composable
fun NavHostTestForMode(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){Text("ModeScreen")}

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            navController.navigate(Routes.QUIZ)
        })
        {
            Text("Go to QuizScreen")
        }
    }

}