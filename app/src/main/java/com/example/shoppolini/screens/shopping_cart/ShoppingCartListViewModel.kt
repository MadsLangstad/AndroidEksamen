package com.example.shoppolini.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderRepository
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShoppingCartListViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        _isLoading.value = true
        loadCartItems()
        _isLoading.value = false
    }

    fun refreshCartItems() {
        viewModelScope.launch {
            loadCartItems()
        }
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

    fun onDeleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                CartRepository.deleteFromCart(productId)
                // Optionally, add some UI feedback here
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }

    fun completePurchase() {
        viewModelScope.launch {
            _cartItems.value.forEach { (product, quantity) ->
                // Create an Order object from the cart item
                val order = Order(
                    id = product.id,
                    title = product.title,
                    quantity = quantity,
                    // Include other necessary fields like price, customer ID, etc.
                    totalPrice = product.price * quantity
                    // Add any other relevant fields from the product or as needed for the Order
                )
                try {
                    OrderRepository.insertOrder(order)
                    // Handle successful insertion (e.g., display a message to the user)
                } catch (e: Exception) {
                    // Handle any exceptions (e.g., display an error message)
                }
            }
            // Optionally, clear the cart after transferring items to the order table
            CartRepository.clearCart()
            // Update cartItems to reflect the empty cart
            _cartItems.value = emptyList()
        }
    }
}
