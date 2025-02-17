package com.example.shoppolini.screens.order_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.OrderLineItem
import com.example.shoppolini.data.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class OrderHistoryViewModel : ViewModel() {

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()


    fun getLineItemsForOrder(orderId: Int): Flow<List<OrderLineItem>> = flow {
        emit(OrderRepository.getOrderLineItems(orderId))
    }


    fun deleteAllOrders() {
        viewModelScope.launch {
            OrderRepository.deleteAllOrders()
            loadOrderHistory()
        }
    }


    init {
        loadOrderHistory()
    }


    private fun loadOrderHistory() {
        viewModelScope.launch {
            OrderRepository.getAllOrders().collect { orderList ->
                _orders.value = orderList
            }
        }
    }
}
