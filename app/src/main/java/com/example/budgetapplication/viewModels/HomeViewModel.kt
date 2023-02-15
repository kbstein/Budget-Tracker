package com.example.budgetapplication.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.budgetapplication.repositories.TransactionRepository


class HomeScreenState {
    var transactionType by mutableStateOf("")
    var transactionDate by mutableStateOf("")
    var transactionAmount by mutableStateOf(0)
    var transactionCategory by mutableStateOf("")
    var transactionDescription by mutableStateOf("")
}

class HomeViewModel(application: Application): AndroidViewModel(application)  {
    val uiState = HomeScreenState()

    suspend fun figureBalance(): String {
        var income: Float = 0.0F
        var expenses: Float = 0.0F
        val theTransactions = TransactionRepository.getTransaction()

        for (currentTransaction in theTransactions) {
            if (currentTransaction.transactionType == "Expense") {
                 currentTransaction.transactionAmount?.let {expenses += currentTransaction.transactionAmount.toFloat() }
            } else if (currentTransaction.transactionType == "Income") {
                currentTransaction.transactionAmount?.let {income += currentTransaction.transactionAmount.toFloat() }
            }
         }
        return String.format("%.2f", (income - expenses))

    }

}