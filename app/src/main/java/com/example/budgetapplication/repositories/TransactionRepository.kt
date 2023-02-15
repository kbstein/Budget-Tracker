package com.example.budgetapplication.repositories

import com.example.budgetapplication.model.Transaction
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object TransactionRepository {

    suspend fun createTransaction(
        transactionType: String,
        transactionDate: String,
        transactionAmount: String,
        transactionCategory: String,
        transactionDescription: String,
    ): Transaction {
        val doc = Firebase.firestore.collection("transaction").document()
        val transaction = Transaction(
            transactionType = transactionType,
            transactionDate = transactionDate,
            transactionAmount = transactionAmount,
            transactionCategory = transactionCategory,
            transactionDescription = transactionDescription,
            id = doc.id,
            userId = UserRepository.getCurrentUserID()
        )
        doc.set(transaction).await()
        transactionCache.add(transaction)
        return transaction

    }

    private val transactionCache = mutableListOf<Transaction>()
    private var cacheInizialized = false

    suspend fun getTransaction(): List<Transaction> {
        if (!cacheInizialized) {
            cacheInizialized = true
            val snapshot = Firebase.firestore.collection("transaction")
                .whereEqualTo("userId", UserRepository.getCurrentUserID())
                .get()
                .await()
            transactionCache.addAll(snapshot.toObjects<Transaction>())
        }
        return transactionCache
    }

    suspend fun updateTransaction(transaction: Transaction) {
        Firebase.firestore.collection("transaction")
            .document(transaction.id!!).set(transaction).await()

        val oldTransactionIndex = transactionCache.indexOfFirst {
            it.id == transaction.id
        }
        transactionCache[oldTransactionIndex] = transaction
    }
    suspend fun deleteTransaction(transaction: Transaction) {
        Firebase.firestore.collection("transaction")
            .document(transaction.id!!).delete()

        for (currentTransaction in transactionCache) {
            if (transaction == currentTransaction) {
                transactionCache.remove(currentTransaction)
                return
            }
        }
    }
}