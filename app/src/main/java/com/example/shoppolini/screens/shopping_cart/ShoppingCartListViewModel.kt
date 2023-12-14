package com.example.shoppolini.screens.shopping_cart
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.CartRepository
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderLineItem
import com.example.shoppolini.data.OrderRepository
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingCartListViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: StateFlow<List<Pair<Product, Int>>> = _cartItems.asStateFlow()


    val totalPrice: StateFlow<Double> = _cartItems.map { list ->
        list.sumOf { (product, quantity) -> product.price * quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)


    init {
        loadCartItems()
    }


    private fun generateOrderId(): Int {
        return (System.currentTimeMillis() % Integer.MAX_VALUE).toInt()
    }


    fun refreshCartItems() {
        viewModelScope.launch {
            loadCartItems()
        }
    }


    private fun loadCartItems() {
        viewModelScope.launch {
            CartRepository.getCartItems().collect { cartList ->
                val productList = convertCartListToProductList(cartList)
                _cartItems.value = productList
            }
        }
    }


    private suspend fun convertCartListToProductList(cartList: List<Cart>): List<Pair<Product, Int>> {
        val productList = mutableListOf<Pair<Product, Int>>()
        cartList.forEach { cartItem ->
            val product = ProductRepository.getProductById(cartItem.productId)
            productList.add(product to cartItem.quantity)
        }
        return productList
    }

    fun onDeleteProduct(cartItemId: Int) {
        viewModelScope.launch {
            try {
                CartRepository.deleteFromCart(cartItemId)
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
