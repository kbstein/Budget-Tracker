package com.example.budgetapplication.navigation

data class Screen(val route: String)

object Routes {
    val TransactionScreen = Screen("transactionScreen")
    val TransactionNavigation = Screen("transactionNavigation")
    val TransactionModificationNavigation = Screen("transactionModificationNavigation")
    val TransactionModificationScreen = Screen("transactionModificationScreen")
    val SignInScreen = Screen("signInScreen")
    val SignUpScreen = Screen("signUpScreen")
    val SplashScreen = Screen("splashScreen")
    val LaunchScreen = Screen("launchScreen")
    val LaunchNavigation = Screen("launchNavigation")
    val HomeScreen = Screen("homeScreen")
}