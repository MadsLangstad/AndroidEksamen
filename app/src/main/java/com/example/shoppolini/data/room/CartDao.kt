package com.example.shoppolini.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shoppolini.data.Cart

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToCart(cartItem: Cart)

    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<Cart>

    @Query("DELETE FROM cart")
    suspend fun clearCart()

}

