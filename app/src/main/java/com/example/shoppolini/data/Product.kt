package com.example.shoppolini.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

