package com.example.shoppolini.screens.product_details

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ProductDetailsViewModel : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()


    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedProduct.value = ProductRepository.getProductById(productId)
        }
    }

    fun addToCart(context: Context, product: Product, quantity: Int = 1) {
        viewModelScope.launch {
            val existingCartItem = CartRepository.getCartItemByProductId(product.id)
            if (existingCartItem == null) {
                CartRepository.addToCart(
                    Cart(
                        id = 0,
                        title = product.title,
                        productTitle = product.title,
                        price = product.price,
                        description = product.description,
                        category = product.category,
                        image = product.image,
                        productId = product.id,
                        quantity = quantity
                    )
                )
                Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show()
            } else {
                val cartItem = existingCartItem.copy(quantity = existingCartItem.quantity + quantity)
                CartRepository.addToCart(cartItem)
                Toast.makeText(context, "Increased quantity in cart by 1", Toast.LENGTH_SHORT).show()

            }
        }
    }
}