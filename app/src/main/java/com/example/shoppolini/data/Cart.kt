package com.example.shoppolini.data

import android.icu.text.CaseMap.Title
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class Cart(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val productTitle: String,
    val productId: Int,
    val quantity: Int
)