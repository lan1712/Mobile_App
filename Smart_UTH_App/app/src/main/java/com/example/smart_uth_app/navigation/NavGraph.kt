package com.example.smart_uth_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.smart_uth_app.ui.screens.detail.DetailScreen
import com.example.smart_uth_app.ui.screens.HomeScreen
import com.example.smart_uth_app.ui.screens.login.ForgotPasswordScreen
import com.example.smart_uth_app.ui.screens.login.LoginScreen
import com.example.smart_uth_app.ui.screens.login.RegisterScreen
import com.example.smart_uth_app.ui.screens.profile.ProfileConfirmScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Routes.Login) {

        composable(Routes.Login) {
            LoginScreen(
                onSuccess = {
                    navController.navigate(Routes.ProfileConfirm) {
                        popUpTo(Routes.Login) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.Register) {
            RegisterScreen(
                onSuccess = {
                    navController.navigate(Routes.ProfileConfirm) { popUpTo(Routes.Login) { inclusive = true } }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.ForgotPassword) { ForgotPasswordScreen(onBack = { navController.popBackStack() }) }

        composable(Routes.Home) { HomeScreen(onOpenDetail = { id -> navController.navigate(Routes.Detail.replace("{id}", "$id")) }) }

        composable(Routes.Detail) { entry ->
            val id = entry.arguments?.getString("id")?.toIntOrNull() ?: -1
            DetailScreen(id = id, onBack = { navController.popBackStack() })
        }

        composable(Routes.ProfileConfirm) {
            ProfileConfirmScreen(
                onBack = { navController.popBackStack() },
                onConfirm = {
                    navController.navigate(Routes.Home) {
                        popUpTo(Routes.ProfileConfirm) { inclusive = true }
                    }
                }
            )
        }
    }
}
