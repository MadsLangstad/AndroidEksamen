package com.example.shoppolini.screens.order_history

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier


@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel
) {

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Order History", style = MaterialTheme.typography.titleLarge)

        }
}

