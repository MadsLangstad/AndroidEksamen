package com.example.shoppolini.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppolini.data.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: Cart)

    @Query("SELECT * FROM cart")
    fun getCartItems(): Flow<List<Cart>>

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    suspend fun deleteFromCart(cartItemId: Int)
}
