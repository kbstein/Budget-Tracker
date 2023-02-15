package com.example.budgetapplication.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budgetapplication.repositories.TransactionRepository

class TransactionModificationScreenState {
    var dropdownExpanded by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var saveSuccess by mutableStateOf(false)
    var transactionType by mutableStateOf("")
    var transactionDate by mutableStateOf("")
    var transactionAmount by mutableStateOf("")
    var transactionCategory by mutableStateOf("")
    var transactionDescription by mutableStateOf("")
    var transactionError by mutableStateOf(false)

}

class TransactionModificationViewModel(application: Application): AndroidViewModel(application) {
    val uiState = TransactionModificationScreenState()
    var id: String? = null
    suspend fun setupInitialState(id: String?) {
        if (id == null || id == "new") return
        this.id = id
        val transaction = TransactionRepository.getTransaction().find { it.id == id } ?: return
        uiState.transactionType = transaction.transactionType ?: ""
        uiState.transactionDate = transaction.transactionDate ?: ""
        uiState.transactionAmount = transaction.transactionAmount ?: ""
        uiState.transactionCategory = transaction.transactionCategory ?: ""
        uiState.transactionDescription = transaction.transactionDescription ?: ""
    }
    suspend fun saveTransaction() {
        uiState.errorMessage = ""
        uiState.transactionError = false
        var temporaryAmount = ""

        // Remove commas
        for (character in uiState.transactionAmount) {
            if (character.toString() != ",") {
                temporaryAmount += character
            }
        }
        uiState.transactionAmount = temporaryAmount

        // Check for errors
        if (uiState.transactionType.isEmpty()
            || uiState.transactionDate.isEmpty() || uiState.transactionCategory.isEmpty()
            || uiState.transactionDescription.isEmpty()) {
            uiState.transactionError = true
            uiState.errorMessage = "Fields cannot be blank"
            return
        }

        if (!uiState.transactionDate.contains("/") || uiState.transactionDate.length != 10) {
            uiState.errorMessage = "Date entered incorrectly"
            uiState.transactionError = true
        }

        if (uiState.transactionAmount.contains(",")) {
            uiState.errorMessage = "Do not use commas in amount"
            uiState.transactionError = true
        }

        // Check for too many numbers after decimal
        var amountLengthCheck = 0
        for (character in uiState.transactionAmount) {
            amountLengthCheck += 1
            if (character.toString() == ".") {
                if (uiState.transactionAmount.length - amountLengthCheck > 2) {
                    uiState.errorMessage = "Enter amount with two decimals"
                    uiState.transactionError = true
                }
            }
        }

        if (!uiState.transactionError) {
            if (id == null) {
                TransactionRepository.createTransaction(
                    transactionType = uiState.transactionType,
                    transactionDate = uiState.transactionDate,
                    transactionAmount = uiState.transactionAmount,
                    transactionCategory = uiState.transactionCategory,
                    transactionDescription = uiState.transactionDescription)
            } else {
                val transaction = TransactionRepository.getTransaction().find { it.id == id } ?: return
                TransactionRepository.updateTransaction(
                    transaction.copy(
                        transactionType = uiState.transactionType,
                        transactionDate = uiState.transactionDate,
                        transactionAmount = uiState.transactionAmount,
                        transactionCategory = uiState.transactionCategory,
                        transactionDescription = uiState.transactionDescription
                    )
                )
            }
            uiState.saveSuccess = true
        }
    }
}