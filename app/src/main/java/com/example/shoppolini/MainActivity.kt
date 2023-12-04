package com.example.shoppolini

import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle

import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.ProductRepository
import com.example.shoppolini.screens.order_history.OrderHistoryScreen
import com.example.shoppolini.screens.order_history.OrderHistoryViewModel
import com.example.shoppolini.screens.product_details.ProductDetailsScreen
import com.example.shoppolini.screens.product_details.ProductDetailsViewModel
import com.example.shoppolini.screens.product_overview.ProductListScreen
import com.example.shoppolini.screens.product_overview.ProductListViewModel
import com.example.shoppolini.screens.shopping_cart.ShoppingCartListScreen
import com.example.shoppolini.screens.shopping_cart.ShoppingCartListViewModel
import com.example.shoppolini.ui.theme.ShoppoliniTheme

class MainActivity : ComponentActivity() {
    private val _productListViewModel: ProductListViewModel by viewModels()
    private val _productDetailsViewModel: ProductDetailsViewModel by viewModels()
    private val _shoppingCartListViewModel: ShoppingCartListViewModel by viewModels()
    private val _orderHistoryViewModel: OrderHistoryViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ProductRepository.initiateAppDatabase(applicationContext)
        CartRepository.initiateAppDatabase(applicationContext)


        setContent {
            ShoppoliniTheme {
                val navController = rememberNavController()

                Scaffold(
                    topBar = {
                        MyAppBar(
                            title = "Shoppolini",
                            onOrderHistoryClick = { navController.navigate("orderHistoryScreen") },
                            onCartClick = { navController.navigate("shoppingCartListScreen") },
                            viewModel = _shoppingCartListViewModel
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "productListScreen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = "productListScreen") {
                            ProductListScreen(
                                viewModel = _productListViewModel,
                                navController = navController,
                                onProductClick = { productId ->
                                    navController.navigate("productDetailsScreen/$productId")
                                }
                            )
                        }
                        composable(
                            route = "productDetailsScreen/{productId}",
                            arguments = listOf(
                                navArgument(name = "productId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                            LaunchedEffect(productId) {
                                _productDetailsViewModel.setSelectedProduct(productId)
                            }

                            ProductDetailsScreen(
                                viewModel = _productDetailsViewModel,
                                onBackButtonClick = { navController.popBackStack() }
                            )
                        }

                        composable(route = "shoppingCartListScreen") {
                            ShoppingCartListScreen(viewModel = _shoppingCartListViewModel)
                        }

                        composable(route = "orderHistoryScreen") {
                            OrderHistoryScreen(viewModel = _orderHistoryViewModel)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    title: String,
    onCartClick: () -> Unit,
    onOrderHistoryClick: () -> Unit,
    viewModel: ShoppingCartListViewModel
) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            CartIconBadge(viewModel = viewModel, onCartClick = onCartClick)
            IconButton(onClick = { onOrderHistoryClick() }) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Order History"
                )
            }
        }
    )
}

@Composable
fun CartIconBadge(
    viewModel: ShoppingCartListViewModel,
    onCartClick: () -> Unit
) {
    val cartProducts by viewModel.cartItems.collectAsState()
    IconButton(onClick = { onCartClick() }) {
        Box(contentAlignment = Alignment.TopEnd) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Cart",
                modifier = Modifier.size(24.dp)
            )
            if (cartProducts.isNotEmpty()) {
                Text(
                    text = cartProducts.size.toString(),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .offset(x = 6.dp, y = (-6).dp)
                        .background(Color.Red, shape = CircleShape)
                        .padding(4.dp)
                )
            }
        }
    }
}
