package com.example.shoppolini.screens.shopping_cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppolini.data.Product

@Composable
fun ShoppingCartListScreen(
    viewModel: ShoppingCartListViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Shopping Cart",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Divider()

        LazyColumn {
            items(cartItems) { (product, quantity) ->
                CartProductItem(product = product, quantity = quantity)
            }
        }
    }
}

@Composable
fun CartProductItem(product: Product, quantity: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = product.title, style = MaterialTheme.typography.titleLarge)
        Text(text = "Price: ${product.price}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Quantity: $quantity", style = MaterialTheme.typography.titleLarge)
        Text(text = "Total: ${product.price * quantity}", style = MaterialTheme.typography.titleLarge)
    }

}
