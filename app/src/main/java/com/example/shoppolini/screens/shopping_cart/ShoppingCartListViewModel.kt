package com.example.shoppolini.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartListViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems.asStateFlow()

    init {
        loadCartItems()
    }

    fun loadCartItems() {
        viewModelScope.launch {
            val cartList = CartRepository.getCartItems()
            val productList = cartList.map { cartItem ->
                val product = ProductRepository.getProductById(cartItem.productId)
                product to cartItem.quantity // Pair of Product and quantity
            }
            _cartItems.value = productList
        }
    }
}
