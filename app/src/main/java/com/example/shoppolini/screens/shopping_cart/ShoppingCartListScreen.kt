package com.example.shoppolini.screens.shopping_cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.shoppolini.data.Cart


@Composable
fun ShoppingCartListScreen(
    viewModel: ShoppingCartListViewModel = viewModel(),
    navController: NavController
) {
    val cartItems by viewModel.cartItems.collectAsState(initial = emptyList())
    val canCompletePurchase = cartItems.isNotEmpty()
    val totalPrice by viewModel.totalPrice.collectAsState(initial = 0.0)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Shopping Cart",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Total: $$totalPrice",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )

        Button(
            onClick = {
                if(canCompletePurchase) {
                    viewModel.completePurchase()
                }
            },
            enabled = canCompletePurchase,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Complete Purchase")
        }

        Divider()

        LazyColumn {
            items(cartItems) { cartItem ->
                CartProductItem(
                    cartItem = cartItem,
                    onDeleteClick = { viewModel.onDeleteProduct(cartItem.id) },
                    onProductClick = {
                        navController.navigate("productDetailsScreen/${cartItem.productId}")
                    }
                )
            }
        }
    }
}


@Composable
fun CartProductItem(
    cartItem: Cart,
    onDeleteClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(10))
            .background(color = Color.White)
            .clickable { onProductClick(cartItem.productId) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Product Image
        AsyncImage(
            modifier = Modifier
                .size(108.dp, 108.dp)
                .background(color = Color.Gray),
            model = cartItem.image,
            contentScale = ContentScale.Crop,
            contentDescription = "Image of ${cartItem.title}"
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
                text = cartItem.title,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Price: ${cartItem.price}",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Quantity: ${cartItem.quantity}", // Access quantity directly from cartItem
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
        }
        // Remove Button
        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove")
        }
    }
}
