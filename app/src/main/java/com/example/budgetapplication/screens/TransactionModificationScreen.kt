package com.example.budgetapplication.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budgetapplication.navigation.Routes
import com.example.budgetapplication.viewModels.TransactionModificationViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun TransactionModificationScreen(navHostController: NavHostController, id: String?, chosenType: String?) {

    val viewModel: TransactionModificationViewModel = viewModel()
    val state = viewModel.uiState
    val scope = rememberCoroutineScope()

    if (chosenType != null) {
        state.transactionType = chosenType.toString()
    }

    LaunchedEffect(true) {
        viewModel.setupInitialState(id)
    }

    LaunchedEffect(state.saveSuccess) {
        if (state.saveSuccess) {
            navHostController.popBackStack()
            navHostController.navigate(Routes.TransactionScreen.route)
        }
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .absolutePadding(15.dp, 10.dp, 15.dp, 50.dp),
    verticalArrangement = Arrangement.Bottom) {
        Text(modifier = Modifier.absolutePadding(25.dp, 10.dp, 25.dp, 100.dp),
            textAlign = TextAlign.Center ,
            text = "Enter Your Transaction",
            fontSize = 50.sp
        )
        transactionTypeField(
            value = state.transactionType,
            onValueChange = { state.transactionType = it },
            placeholder = { Text(text = state.transactionType) }
        )
        Spacer(modifier = Modifier.height(4.dp))
        FormField(
            value = state.transactionDate,
            onValueChange = { state.transactionDate = it },
            placeholder = { Text(text = "Date: 'MM/DD/YYYY'") }
        )
        Spacer(modifier = Modifier.height(4.dp))
        NumberField(
            value = state.transactionAmount,
            onValueChange = { state.transactionAmount = it }
        ) { Text(text = "Amount") }
        Spacer(modifier = Modifier.height(8.dp))
        FormField(
            value = state.transactionCategory,
            onValueChange = { state.transactionCategory = it },
            placeholder = { Text(text = "Category") }
        )
        Spacer(modifier = Modifier.height(4.dp))
        FormField(
            value = state.transactionDescription,
            onValueChange = { state.transactionDescription = it },
            placeholder = { Text(text = "Description") }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ){
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = state.errorMessage,
            style = TextStyle(color = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(colors =
            ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
                onClick = {
                    scope.launch {
                        navHostController.navigate(Routes.HomeScreen.route)
                    }
                }) {
                Text(text = "Cancel")
            }
            Spacer(modifier = Modifier.padding(100.dp))
            Button(colors =
            ButtonDefaults.buttonColors(backgroundColor = Color.Blue, contentColor = Color.White),
                onClick = {
                scope.launch {
                    viewModel.saveTransaction()
                }
            }) {
                Text(text = "Save")
            }
        }

    }
}


@Composable
fun FormField(
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit,
    error: Boolean = false,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        modifier = Modifier.fillMaxWidth(),
        isError = error
    )
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun NumberField(
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit,
) {
    TextField(
        value = value,
        modifier = Modifier.fillMaxWidth(),
        placeholder = placeholder,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        onValueChange = onValueChange
    )}

@Composable
fun transactionTypeField(
    value: String,
    onValueChange: (value: String) -> Unit,
    placeholder: @Composable () -> Unit
    ): String {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var dropdownSelected by remember { mutableStateOf("") }
    val icon = if (dropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown
    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = dropdownSelected,
            onValueChange = onValueChange,
            label = placeholder,
            trailingIcon = {
                Icon(icon,"contentDescription",
                    Modifier.clickable { dropdownExpanded = !dropdownExpanded })
            }
        )
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false }
        ) {
            listOf<String>("Income", "Expense").forEach() { label ->
                DropdownMenuItem(onClick = {
                    dropdownSelected = label
                    dropdownExpanded = false
                }) {
                    Text(text = label)
                }
            }
        }
    }
    return dropdownSelected
}

