package com.example.shoppolini.screens.shopping_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
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
        Button(
            onClick = { viewModel.completePurchase() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Complete Purchase")
        }
        IconButton(onClick = { viewModel.refreshCartItems() }) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
        }
        Divider()

        LazyColumn {
            items(cartItems) { (product, quantity) ->
                CartProductItem(product = product, quantity = quantity)
            }
        }
    }
}

@Composable
fun CartProductItem(
    product: Product,
    quantity: Int,
    viewModel: ShoppingCartListViewModel = viewModel()
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
            .background(color = Color.White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray),
            model = product.image,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${product.title}"
        )
        // Product Details
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = product.title,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: ${product.price}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Quantity: $quantity",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        // Remove Button
        Column(
            modifier = Modifier.align(Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = {
                    viewModel.onDeleteProduct(product.id)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Delete, // Replace with an appropriate icon
                    contentDescription = "Remove"
                )
            }
        }
    }
}
