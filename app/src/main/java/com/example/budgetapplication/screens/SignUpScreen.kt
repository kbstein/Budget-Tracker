package com.example.budgetapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budgetapplication.viewModels.SignUpViewModel
import kotlinx.coroutines.launch

var accountCreated by mutableStateOf(false)

@Composable
fun SignUpScreen(navHostController: NavHostController) {

    val viewModel: SignUpViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.signUpSuccess) {
        if (state.signUpSuccess) {
            navHostController.navigate("transactionScreen") {
                popUpTo(0)
            }
        }
    }
    LaunchedEffect(accountCreated) {
        if (accountCreated) {
            scope.launch{viewModel.signUp()}
        }
        if (!state.signUpSuccess) {
            accountCreated = false
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), verticalArrangement = Arrangement.SpaceAround) {
        Surface(elevation = 2.dp) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {

                Row() {
                    Text(text = "Create Account", fontSize = 20.sp)
                }

                state.email = emailBox(label = "Enter Email")
                state.emailConfirmation = emailBox(label = "Confirm Email")
                state.password = passwordBox(label = "Enter Password")
                state.passwordConfirmation = passwordBox(label = "Confirm Password")

                Text(text = viewModel.uiState.errorMessage, color = Color.Red)

                CreateAccountButtons()

            }
        }
    }
}

@Composable
fun emailBox(label: String): String {
    var userEmail by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(value = userEmail, onValueChange = { userEmail = it },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth())
    }
    return userEmail
}

@Composable
fun passwordBox(label: String): String {
    var userPassword by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        TextField(value = userPassword, onValueChange = { userPassword = it },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth())
    }
    return userPassword
}

@Composable
fun CreateAccountButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Box(Modifier.padding(10.dp)) {
            Button(onClick = { println(accountCreated)}, modifier = Modifier.padding()) {
                Text(text = "Cancel")
            }
        }
        Box(Modifier.padding(10.dp)) {
            Button(onClick = {
                accountCreated = true
            },
                modifier = Modifier.padding() ) {
                Text(text = "Create Account")
            }
        }
    }
}
