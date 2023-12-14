package com.example.shoppolini.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val productTitle: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val productId: Int,
    val quantity: Int
)
