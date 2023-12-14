package com.example.shoppolini.screens.product_overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductListViewModel : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()


    init {
        loadProducts()
    }


    fun loadProducts() {
        viewModelScope.launch {
            _products.value = ProductRepository.getProducts()
        }
    }


    fun onBuyProduct(productId: Int, quantity: Int, productTitle: String) {
        viewModelScope.launch {
            try {
                val cartItem = Cart(productId = productId, quantity = quantity, productTitle = productTitle)
                CartRepository.addToCart(cartItem)
                // Optionally, add some UI feedback here
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}