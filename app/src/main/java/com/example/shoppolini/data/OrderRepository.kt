package com.example.shoppolini.data

import android.content.Context
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase

object OrderRepository {

    private lateinit var _appDatabase: AppDatabase

    // Initialize the AppDatabase instance
    fun initiateAppDatabase(context: Context) {
        if (!::_appDatabase.isInitialized) {
            _appDatabase = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "appDatabase"
            ).fallbackToDestructiveMigration()
                .build()
        }
    }


    // Add a catItem to orderHistory
    suspend fun insertOrder(order: Order) {
        try {
            _appDatabase.orderDao().insertOrder(order)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Get all the orders
    suspend fun getAllOrders(): List<Order> {
        return try {
            _appDatabase.orderDao().getAllOrders()
        } catch (e: Exception) {
            emptyList()
        }
    }

}