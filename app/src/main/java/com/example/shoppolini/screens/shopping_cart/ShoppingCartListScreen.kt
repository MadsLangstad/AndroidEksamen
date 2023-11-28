package com.example.shoppolini.screens.shopping_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.shoppolini.data.Product

@Composable
fun ShoppingCartListScreen(
    viewModel: ShoppingCartListViewModel
) {
    val cartProducts by viewModel.cartProducts.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        if (isLoading) {

            CircularProgressIndicator()
        } else {
            if (errorMessage != null) {
                // Show error message
                Text(text = errorMessage!!, color = Color.Red)
            }

            LazyColumn {
                items(cartProducts) { product ->
                    CartProductItem(product = product)
                    Divider()
                }
            }

            // Total price
            Text(
                text = "Total Price: $totalPrice",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun CartProductItem(product: Product) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = product.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "Price: ${product.price}", style = MaterialTheme.typography.titleLarge)
        // Add more product details or actions like remove button
    }
}
