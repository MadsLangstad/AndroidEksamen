package com.example.shoppolini.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {
    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    init {
        loadOrderHistory()
    }

    private fun loadOrderHistory() {
        viewModelScope.launch {
            // Fetch orders from repository and update _orders
            val orderList = com.example.shoppolini.data.OrderRepository.getAllOrders()
            _orders.value = orderList
        }
    }
}
