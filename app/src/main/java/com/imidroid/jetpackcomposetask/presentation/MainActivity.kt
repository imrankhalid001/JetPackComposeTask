package com.imidroid.jetpackcomposetask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.imidroid.jetpackcomposetask.presentation.screen.product.ProductManager
import com.imidroid.jetpackcomposetask.presentation.ui.theme.JetpackComposeTaskTheme
import com.imidroid.jetpackcomposetask.presentation.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeTaskTheme {
                val viewModel : ProductViewModel = viewModel()
                ProductManager(viewModel)
            }
        }
    }
}
