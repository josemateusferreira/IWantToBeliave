package com.example.iwanttobelieve.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.iwanttobelieve.ui.auth.LoginScreen
import com.example.iwanttobelieve.ui.auth.RegisterScreen
import com.example.iwanttobelieve.ui.navigation.Destinations
import com.example.iwanttobelieve.ui.navigation.MainScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(navController)
        }
        composable(Destinations.REGISTER) {
            RegisterScreen(navController)
        }
        composable(Destinations.FEED) {
            MainScreen()
        }
    }
}
