package com.example.shoppolini.data

data class Order(
    val id: Int,
    val date: String,
    val totalAmount: Double,
    val items: List<OrderItem>,
    val status: OrderStatus
)

data class OrderItem(
    val productId: Int,
    val productName: String,
    val quantity: Int,
    val pricePerUnit: Double
)

enum class OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELED
}