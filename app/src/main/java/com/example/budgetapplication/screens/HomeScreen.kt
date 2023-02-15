package com.example.budgetapplication.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.budgetapplication.viewModels.HomeViewModel
import com.example.budgetapplication.viewModels.TransactionModificationScreenState
import com.example.budgetapplication.viewModels.TransactionModificationViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

var balance by mutableStateOf("0")

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        balance = viewModel.figureBalance().toString()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(150.dp))
        Text(modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(500),
            fontSize = 50.sp,
            text = "Balance")
        Box(modifier = Modifier
            .padding(horizontal = 75.dp)
            .height(55.dp)
            .border(2.dp, color = Color(0xFF0000FF), shape = RoundedCornerShape(10.dp))
        , contentAlignment = Center) {
            Text(modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                text = "$$balance"
            )
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(
                Modifier
                    .padding(10.dp)
                    .size(150.dp, 75.dp)) {
                Button(colors =
                ButtonDefaults.buttonColors(contentColor = Color.White),
                    onClick = {
                        navHostController.navigate("settransactiontype?type=Income")
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()) {
                    Text(fontSize = 20.sp, text = "Income")
                }
            }
            Box(
                Modifier
                    .padding(10.dp)
                    .size(150.dp, 75.dp)) {
                Button(colors =
                ButtonDefaults.buttonColors(contentColor = Color.White),
                    onClick = {
                        navHostController.navigate("settransactiontype?type=Expense")
                              },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()) {
                    Text(fontSize = 20.sp, text = "Expense")
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Box(
                Modifier
                    .padding(10.dp)
                    .size(320.dp, 75.dp)) {
                Button(colors =
                ButtonDefaults.buttonColors(contentColor = Color.White),
                    onClick = {
                    navHostController.navigate("transactionScreen")
                },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding()) {
                    Text(fontSize = 20.sp, text = "Transactions")
                }
            }
        }
        AndroidView(modifier = Modifier
            .fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.BANNER)
                    adUnitId = "ca-app-pub-3940256099942544/6300978111"
                    loadAd(AdRequest.Builder().build())
                }
            },
        )
    }
}
