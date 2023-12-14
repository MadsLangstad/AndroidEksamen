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


    private fun loadProducts() {
        viewModelScope.launch {
            _products.value = ProductRepository.getProducts()
        }
    }


    fun onBuyProduct(productId: Int, quantity: Int) {
        viewModelScope.launch {
            try {
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

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}