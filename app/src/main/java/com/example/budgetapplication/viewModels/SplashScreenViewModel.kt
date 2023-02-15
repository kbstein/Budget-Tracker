package com.example.budgetapplication.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.budgetapplication.repositories.UserRepository

class SplashScreenViewModel(application: Application): AndroidViewModel(application) {
    fun isUserLoggedIn() = UserRepository.isUserLoggedIn()
}