package com.example.shoppolini.screens.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderLineItem
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

    private fun generateOrderId(): Int {
        return (System.currentTimeMillis() % Integer.MAX_VALUE).toInt()
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
                product to cartItem.quantity
            }
            _cartItems.value = productList
        }
    }

    fun onDeleteProduct(productId: Int) {
        viewModelScope.launch {
            try {
                CartRepository.deleteFromCart(productId)
            } catch (e: Exception) {

            }
        }
    }

    fun completePurchase() {
        viewModelScope.launch {
            val totalOrderPrice = _cartItems.value.sumOf { (product, quantity) -> product.price * quantity }

            val order = Order(
                id = generateOrderId(),
                totalPrice = totalOrderPrice
            )

            try {
                OrderRepository.insertOrder(order)

                _cartItems.value.forEach { (product, quantity) ->
                    val orderLineItem = OrderLineItem(
                        orderId = order.id,
                        productId = product.id,
                        productTitle = product.title,
                        quantity = quantity,
                        price = product.price
                    )
                    OrderRepository.insertOrderLineItem(orderLineItem)
                }

                CartRepository.clearCart()

                _cartItems.value = emptyList()
            } catch (e: Exception) {
                // Handle
            }
        }
    }

}
