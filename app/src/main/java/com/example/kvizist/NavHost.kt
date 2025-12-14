package com.example.kvizist

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNav() {
    val navController = rememberNavController()
    val sharedViewModel: dataFromAnotherScreen = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen(navController, viewModel = sharedViewModel) }
        composable("quiz") { QuizScreen(navController, viewModel = sharedViewModel) }
    }
    }


