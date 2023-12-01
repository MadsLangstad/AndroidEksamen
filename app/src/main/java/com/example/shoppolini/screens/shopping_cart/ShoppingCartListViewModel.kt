package com.example.shoppolini.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartListViewModel() : ViewModel() {

    private lateinit var _appDatabase: AppDatabase
    private val _cartProducts = MutableStateFlow<List<Product>>(emptyList())
    val cartProducts: StateFlow<List<Product>> = _cartProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice = _totalPrice.asStateFlow()


    fun addToCart(productId: Int) {
        viewModelScope.launch {
            val cartItem = Cart(productId = productId, quantity = 1)
            _appDatabase.cartDao().addToCart(cartItem)
        }
    }

    fun checkout() {
        viewModelScope.launch {
            val cartItems = _appDatabase.cartDao().getCartItems()
            cartItems.forEach { cartItem ->
                val product = _appDatabase.productDao().getProductById(cartItem.productId)
                val order = Order(id = 0, title = product.title, totalPrice = product.price * cartItem.quantity, quantity = cartItem.quantity)
                _appDatabase.orderDao().insertOrder(order)
            }
            _appDatabase.cartDao().clearCart()
        }
    }




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
