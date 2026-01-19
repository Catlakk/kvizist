
package com.example.kvizist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kvizist.ui.QuizScreen
import com.example.kvizist.ui.StatisticsScreen

@Composable
fun AppNav(
    sessionData: SessionData = viewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        composable(Routes.HOME) { HomeScreen(navController, sessionData) }
        composable(Routes.QUIZ) { QuizScreen(navController, sessionData) }
        composable(Routes.QUIZ_MODE) { ModeScreen(navController, sessionData) }
        composable(Routes.STATISTICS) { StatisticsScreen(navController, sessionData) }
    }
}

