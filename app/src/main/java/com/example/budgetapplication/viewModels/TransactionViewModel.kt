package com.example.budgetapplication.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budgetapplication.model.Transaction
import com.example.budgetapplication.repositories.TransactionRepository

class TransactionScreenState {
    val _transaction = mutableStateListOf<Transaction>()
    val transaction: List<Transaction> get() = _transaction
    var transactionType by mutableStateOf("")
    var transactionDate by mutableStateOf("")
    var transactionAmount by mutableStateOf("")
    var transactionCategory by mutableStateOf("")
    var transactionDescription by mutableStateOf("")
    var transactionDeletion by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
}


class TransactionViewModel(application: Application): AndroidViewModel(application) {

    val uiState  = TransactionScreenState()

    suspend fun toCreateTransaction() {
        TransactionRepository.createTransaction(
            transactionType = uiState.transactionType,
            transactionDate = uiState.transactionDate,
            transactionAmount = uiState.transactionAmount,
            transactionCategory = uiState.transactionCategory,
            transactionDescription = uiState.transactionDescription
        )
    }

    suspend fun getTransactionFromRepo() {
        uiState._transaction.clear()
        val transaction = TransactionRepository.getTransaction()
        uiState._transaction.addAll(transaction)
    }

    suspend fun deleteTransactionFromRepo(transaction: Transaction) {
        TransactionRepository.deleteTransaction(transaction)
        uiState.transactionDeletion = true
    }
}