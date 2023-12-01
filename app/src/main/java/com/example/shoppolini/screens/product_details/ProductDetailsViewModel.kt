package com.example.shoppolini.screens.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.shoppolini.data.Product
import com.example.shoppolini.data.ProductRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers

class ProductDetailsViewModel : ViewModel() {

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()


    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun setSelectedProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _selectedProduct.value = ProductRepository.getProductById(productId)
            _isLoading.value = false
        }
    }

}