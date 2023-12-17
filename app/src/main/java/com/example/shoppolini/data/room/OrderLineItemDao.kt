package com.example.shoppolini.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppolini.data.OrderLineItem

@Dao
interface OrderLineItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderLineItem(orderLineItem: OrderLineItem)

    @Query("SELECT * FROM order_line_items WHERE orderId = :orderId")
    suspend fun getOrderLineItemsByOrderId(orderId: Int): List<OrderLineItem>

}