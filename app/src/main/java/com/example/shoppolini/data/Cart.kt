package com.example.shoppolini.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey val id: Int,
    val title: String,
    val totalAmount: Double,
    val quantity: Int,
    val image: String
)