package com.example.shoppolini.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders = _orders.asStateFlow()

    fun loadOrderHistory() {
        viewModelScope.launch {
            try {
                _orders.value = fetchOrders()
            } catch (e: Exception) {
            }
        }
    }

    private fun fetchOrders(): List<Order> {
        return listOf()
    }
}


