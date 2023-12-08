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


        fun loadProducts() {
            viewModelScope.launch {
                _isLoading.value = true
                _products.value = ProductRepository.getProducts()
                _isLoading.value = false
            }
        }


        fun onBuyProduct(productId: Int, quantity: Int, productTitle: String) {
            viewModelScope.launch {
                try {
                    val cartItem = Cart(productId = productId, quantity = quantity, productTitle = productTitle)
                    CartRepository.addToCart(cartItem)
                    // Optionally, add some UI feedback here
                } catch (e: Exception) {
                    // Handle the error appropriately
                }
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

    }