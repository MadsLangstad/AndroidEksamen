package com.example.shoppolini.screens.order_history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.shoppolini.data.Order

@Composable
fun OrderHistoryScreen(
    viewModel: OrderHistoryViewModel
) {
    val orders = viewModel.orders.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text("Order History", style = MaterialTheme.typography.titleLarge)
        LazyColumn {
            items(orders.value) { order ->
                OrderItem(order)
            }
        }
    }
}

@Composable
fun OrderItem(order: Order) {
    Text("Order ID: ${order.id}")
}
