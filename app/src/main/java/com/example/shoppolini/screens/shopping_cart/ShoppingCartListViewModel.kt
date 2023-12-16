package com.example.shoppolini.screens.shopping_cart
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import com.example.shoppolini.data.CartRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderLineItem
import com.example.shoppolini.data.OrderRepository
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ShoppingCartListViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<Pair<Product, Int>>>(emptyList())
    val cartItems: Flow<List<Cart>> = CartRepository.getCartItems()


    val totalPrice: StateFlow<Double> = _cartItems.map { list ->
        list.sumOf { (product, quantity) -> product.price * quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), 0.0)


    init {
        loadCartItems()
    }


    private fun generateOrderId(): Int {
        return (System.currentTimeMillis() % Integer.MAX_VALUE).toInt()
    }


    private fun loadCartItems() {
        viewModelScope.launch {
            CartRepository.getCartItems().collect { cartList ->
                val productList = convertCartListToProductList(cartList)
                _cartItems.value = productList
            }
        }
    }

    fun incrementQuantity(cartItemId: Int) {
        viewModelScope.launch {
            CartRepository.incrementCartItemQuantity(cartItemId)
            loadCartItems() // Refresh cart items
        }
    }

    fun decrementQuantity(cartItemId: Int) {
        viewModelScope.launch {
            CartRepository.decrementCartItemQuantity(cartItemId)
            loadCartItems() // Refresh cart items
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
                Log.d("ShoppingCartListVM", "Attempting to delete cart item: $cartItemId")
                CartRepository.deleteFromCart(cartItemId)
                loadCartItems() // Reload cart items
                Log.d("ShoppingCartListVM", "Cart item deleted and cart reloaded")
            } catch (e: Exception) {
                Log.e("ShoppingCartListVM", "Error deleting cart product: ${e.message}")
            }
        }
    }

    fun completePurchase(context: Context) {
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
                Toast.makeText(context, "Order completed successfully", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error completing the order", Toast.LENGTH_LONG).show()
            }
        }
    }

}
