package com.example.shoppolini.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppolini.data.Cart
import com.example.shoppolini.data.Order
import com.example.shoppolini.data.Product

@Database(
    entities = [Product::class, Cart::class, Order::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
}
