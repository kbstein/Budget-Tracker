package com.example.budgetapplication.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.budgetapplication.SignInScreen
import com.example.budgetapplication.navigation.Routes.LaunchScreen
import com.example.budgetapplication.navigation.Routes.SignInScreen
import com.example.budgetapplication.navigation.Routes.SignUpScreen
import com.example.budgetapplication.navigation.Routes.SplashScreen
import com.example.budgetapplication.navigation.Routes.TransactionModificationNavigation
import com.example.budgetapplication.navigation.Routes.TransactionModificationScreen
import com.example.budgetapplication.navigation.Routes.TransactionScreen
import com.example.budgetapplication.repositories.UserRepository
import com.example.budgetapplication.screens.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RootNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.LaunchNavigation.route || it.route == Routes.SplashScreen.route } == true) {
                TopAppBar() {
                    if (currentDestination?.route == Routes.TransactionScreen.route) {
                        IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu button", tint = Color.White)
                        }
                    } else {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                    Text(text = "Transactions", color = Color.White)
                }
            }
        },
        drawerContent = {
            if (currentDestination?.hierarchy?.none { it.route == Routes.LaunchNavigation.route || it.route == Routes.SplashScreen.route } == true) {
                DropdownMenuItem(onClick = {
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.HomeScreen.route)
                }) {
                    Column {
                        Row {
                            Icon(Icons.Default.Home, "Home")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Home")
                        }
                    }
                }
                DropdownMenuItem(onClick = {
                    UserRepository.signOutUser()
                    scope.launch {
                        scaffoldState.drawerState.snapTo(DrawerValue.Closed)
                    }
                    navController.navigate(Routes.LaunchNavigation.route) {
                        popUpTo(0)
                    }
                }) {
                    Column {
                        Row {
                            Icon(Icons.Default.ExitToApp, "Logout")
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = "Logout")
                        }
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.SplashScreen.route,
            modifier = Modifier.padding(paddingValues = it)
        ) {
            navigation(route = Routes.LaunchNavigation.route, startDestination = Routes.LaunchScreen.route) {
                composable(route = Routes.LaunchScreen.route) { LaunchScreen(navController) }
                composable(route = Routes.SignInScreen.route) { SignInScreen(navController) }
                composable(route = Routes.SignUpScreen.route) { SignUpScreen(navController) }
                composable(route = Routes.TransactionModificationScreen.route) { TransactionModificationScreen(navController, id = null, chosenType = null) }
                composable(route = Routes.HomeScreen.route) { HomeScreen(navController)}
            }
            navigation(route = Routes.TransactionNavigation.route, startDestination = Routes.TransactionScreen.route) {
                composable(
                    route = "edittransaction?id={id}",
                    arguments = listOf(navArgument("id") {defaultValue = "new"})
                ) { navBackStackEntry ->
                    TransactionModificationScreen(navController, navBackStackEntry.arguments?.get("id").toString(), chosenType = null)
                }
                composable(route = Routes.TransactionScreen.route) { TransactionScreen(navController) }
            }
            navigation(route = TransactionModificationNavigation.route, startDestination = Routes.TransactionModificationScreen.route) {
                composable(
                    route = "settransactiontype?type={type}",
                    arguments = listOf(navArgument("type") {defaultValue = "Income"})
                ) { navBackStackEntry ->
                    TransactionModificationScreen(navController, id = null, chosenType = navBackStackEntry.arguments?.get("type").toString())
                }
            }
            composable(route = Routes.SplashScreen.route) { SplashScreen(navController) }
        }
    }
}