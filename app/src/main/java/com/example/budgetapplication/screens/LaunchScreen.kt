package com.example.budgetapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budgetapplication.viewModels.SignInViewModel

@Composable
fun LaunchScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    Column() {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(0.dp, 50.dp)) {
                Text(modifier = Modifier.fillMaxWidth(),
                    text = "Welcome", textAlign = TextAlign.Center,
                    fontSize = 50.sp)
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .padding(5.dp, 50.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navHostController.navigate("SignUpScreen") }) {
                        Text(text = "Sign Up")
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { navHostController.navigate("SignInScreen") }) {
                        Text(text = "Sign In")
                    }
                }
            }
        }
    }
}