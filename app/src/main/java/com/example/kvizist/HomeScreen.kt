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
fun HomeScreen(navController: NavController,sessionData: SessionData ){
    NavHostTestForHome(navController)

    //sessionData test. Zapisana vrijednost treba biti vidljiva u ModeScreen
    sessionData.selectedSubject = "Programsko"
}



@Composable
fun NavHostTestForHome(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){Text("HomeScreen")}

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(onClick = {
            navController.navigate(Routes.QUIZ_MODE)
        })
        {
            Text("Go to ModeScreen")
        }
    }

}