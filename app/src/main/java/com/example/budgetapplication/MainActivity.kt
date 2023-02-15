package com.example.budgetapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.budgetapplication.navigation.RootNavigation
import com.example.budgetapplication.screens.HomeScreen
import com.example.budgetapplication.ui.theme.BudgetApplicationTheme

// App Icon from: "https://www.flaticon.com/free-icons/money" created by Pixel perfect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetApplicationTheme {
                RootNavigation()
            }
        }
    }
}
