package com.example.shoppolini

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ShoppoliniTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "productListScreen") {
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