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


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    init {
        loadProducts()
    }


    private fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _products.value = ProductRepository.getProducts()
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun onBuyProduct(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
                val existingCartItem = CartRepository.getCartItemByProductId(productId)
                if (existingCartItem == null) {
                    val product = ProductRepository.getProductById(productId)
                    val cartItem = Cart(
                    id = 0,
                    title = product.title,
                    productTitle = product.title,
                    price = product.price,
                    description = product.description,
                    category = product.category,
                    image = product.image,
                    productId = productId,
                    quantity = quantity
                )
                    CartRepository.addToCart(cartItem)
                } else {
                    val cartItem = existingCartItem.copy(quantity = existingCartItem.quantity + quantity)
                    CartRepository.addToCart(cartItem)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}