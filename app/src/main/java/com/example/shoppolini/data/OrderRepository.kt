package com.example.shoppolini.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

object OrderRepository {

    private lateinit var _appDatabase: AppDatabase


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

    suspend fun insertOrderLineItem(orderLineItem: OrderLineItem) {
        _appDatabase.orderLineItemDao().insertOrderLineItem(orderLineItem)
    }


    suspend fun insertOrder(order: Order) {
        try {
            _appDatabase.orderDao().insertOrder(order)
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error inserting order: ${e.message}")
        }
    }


    fun getAllOrders(): Flow<List<Order>> {
        return try {
            _appDatabase.orderDao().getAllOrders()
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error fetching orders: ${e.message}")
            throw e
        }
    }


    suspend fun getOrderLineItems(orderId: Int): List<OrderLineItem> {
        return try {
            _appDatabase.orderLineItemDao().getOrderLineItemsByOrderId(orderId)
        } catch (e: Exception) {
            Log.e("OrderRepository", "Error fetching order line items: ${e.message}")
            emptyList()
        }
    }

    suspend fun deleteAllOrders() {
        _appDatabase.orderDao().deleteAllOrders()
    }
}
