package com.example.budgetapplication.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.budgetapplication.navigation.Routes
import com.example.budgetapplication.viewModels.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val viewModel: SplashScreenViewModel = viewModel()
    LaunchedEffect(key1 = true) {

        delay(1000)

        if (viewModel.isUserLoggedIn()) {
            navHostController.navigate(Routes.HomeScreen.route)
        } else {
            navHostController.navigate(Routes.LaunchScreen.route) {
                popUpTo(navHostController.graph.findStartDestination().id) {
                    inclusive = true
                }
            }

        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Your Budget",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}