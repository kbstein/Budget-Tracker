package com.example.budgetapplication.model

data class Transaction(
    val id: String? = null,
    val userId: String? = null,
    val transactionType: String? = null,
    val transactionDate: String? = null,
    val transactionAmount: String? = null,
    val transactionCategory: String? = null,
    val transactionDescription: String? = null,
) {
}