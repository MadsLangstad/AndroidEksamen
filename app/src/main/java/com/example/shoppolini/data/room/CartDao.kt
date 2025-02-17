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

    @Query("SELECT * FROM cart WHERE productId = :productId LIMIT 1")
    suspend fun getCartItemByProductId(productId: Int): Cart?

    @Query("UPDATE cart SET quantity = quantity + 1 WHERE id = :cartItemId")
    suspend fun incrementQuantity(cartItemId: Int)

    @Query("UPDATE cart SET quantity = CASE WHEN quantity > 1 THEN quantity - 1 ELSE 1 END WHERE id = :cartItemId")
    suspend fun decrementQuantity(cartItemId: Int)

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("DELETE FROM cart WHERE id = :cartItemId")
    suspend fun deleteFromCart(cartItemId: Int)
}
