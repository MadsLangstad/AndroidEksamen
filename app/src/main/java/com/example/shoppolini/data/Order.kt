package com.example.shoppolini.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "orders")
data class Order(
    @PrimaryKey val id: Int,
    val title: String,
    var totalPrice: Double,
    var quantity: Int,
)
