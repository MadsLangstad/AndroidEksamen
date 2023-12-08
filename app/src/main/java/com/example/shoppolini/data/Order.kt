package com.example.shoppolini.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: Int,
    var totalPrice: Double
)

@Entity(tableName = "order_line_items")
data class OrderLineItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val orderId: Int,
    val productId: Int,
    val productTitle: String,
    val quantity: Int,
    val price: Double
)
