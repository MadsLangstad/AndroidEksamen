package com.example.shoppolini.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartListViewModel : ViewModel() {
    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun loadCartProducts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _cartProducts.value = listOf()
                _totalPrice.value = _cartProducts.value.sumOf { it.price }
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error loading cart products"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
