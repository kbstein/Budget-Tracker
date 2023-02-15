package com.example.budgetapplication.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.budgetapplication.model.Transaction
import com.example.budgetapplication.viewModels.TransactionViewModel
import kotlinx.coroutines.launch


@Composable
fun TransactionScreen(navHostController: NavHostController) {

    val viewModel: TransactionViewModel = viewModel()
    val state = viewModel.uiState

    LaunchedEffect(true) {
        viewModel.getTransactionFromRepo()
    }

    // Update screen if deletion occurs
    LaunchedEffect(state.transactionDeletion) {
        viewModel.getTransactionFromRepo()
        state.transactionDeletion = false
    }


    LazyColumn(modifier = Modifier
        .padding(16.dp)
        .fillMaxHeight()) {
        items(state.transaction, key = {it.id!!}) { transaction ->
            TransactionListItem(viewModel = viewModel, transaction = transaction,
                onEditPressed = { navHostController.navigate("edittransaction?id=${transaction.id}")})
        }
    }
}

enum class SwipeState {
    OPEN,
    CLOSED
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TransactionListItem(
    transaction: Transaction,
    onEditPressed: () -> Unit = {},
    viewModel: TransactionViewModel
) {
    val borderColor: Color
    if (transaction.transactionType == "Income") {
        borderColor = Color(0xFF00A421)
    } else {
        borderColor = Color(0xFFB30000)
    }
    val scope = rememberCoroutineScope()
    val swipeableState = rememberSwipeableState(initialValue = SwipeState.CLOSED)
    val anchors = mapOf(
        0f to SwipeState.CLOSED,
        -200f to SwipeState.OPEN
    )
    Spacer(modifier = Modifier.height(1.dp))
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .swipeable(
            state = swipeableState,
            anchors = anchors,
            orientation = Orientation.Horizontal
        )
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(147.dp),
        horizontalArrangement = Arrangement.End) {
            Button(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f),
                onClick = {
                          scope.launch { viewModel.deleteTransactionFromRepo(transaction) }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        }
        Surface(modifier = Modifier.offset { IntOffset(swipeableState.offset.value.toInt(), 0) },
            elevation = 2.dp,
            shape = RoundedCornerShape(4.dp), border = BorderStroke(width = 1.dp, color = borderColor),
        ) {
            Column (){
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = ("    Type: " + transaction.transactionType) ?: "")
                    }
                    IconButton(onClick = onEditPressed) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit button")
                    }
                }
                Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Text(text = ("Date: " + transaction.transactionDate) ?: "")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Text(text = ("Amount: " + transaction.transactionAmount) ?: "")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.padding(16.dp, 0.dp)) {
                    Text(text = ("Category: " + transaction.transactionCategory) ?: "")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
    Spacer(modifier = Modifier.height(7.dp))
}