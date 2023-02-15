package com.example.budgetapplication

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import com.example.budgetapplication.navigation.Routes
import com.example.budgetapplication.screens.accountCreated
import com.example.budgetapplication.screens.emailBox
import com.example.budgetapplication.screens.passwordBox
import com.example.budgetapplication.viewModels.SignInViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(navHostController: NavHostController) {
    val viewModel: SignInViewModel = viewModel()
    val scope = rememberCoroutineScope()
    val state = viewModel.uiState
    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navHostController.navigate(Routes.HomeScreen.route) {
                popUpTo(0)
            }
        }
    }
    LaunchedEffect(accountCreated) {
        if (accountCreated) {
            scope.launch{viewModel.signIn()}
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
                    Text(text = "Sign In", fontSize = 20.sp)
                }

                state.email = emailBox(label = "Enter Email")
                state.password = passwordBox(label = "Enter Password")
                Text(text = viewModel.uiState.errorMessage)

                SignInButtons()
            }
        }

    }
}


@Composable
fun SignInButtons() {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Box(Modifier.padding(10.dp)) {
            Button(onClick = { accountCreated = false }, modifier = Modifier.padding()) {
                Text(text = "Cancel")
            }
        }
        Box(Modifier.padding(10.dp)) {
            Button(onClick = {
                accountCreated = true
            },
                modifier = Modifier.padding() ) {
                Text(text = "Sign In")
            }
        }
    }
}