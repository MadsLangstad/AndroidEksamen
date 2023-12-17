package com.example.shoppolini.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

object CartRepository {

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


    suspend fun addToCart(cartItem: Cart) {
        try {
            _appDatabase.cartDao().addToCart(cartItem)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error adding to cart: ${e.message}")
        }
    }


    fun getCartItems(): Flow<List<Cart>> {
        return try {
            _appDatabase.cartDao().getCartItems()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error getting cart items: ${e.message}")
            throw e
        }
    }


    suspend fun getCartItemByProductId(productId: Int): Cart? {
        return try {
            _appDatabase.cartDao().getCartItemByProductId(productId)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error checking cart item: ${e.message}")
            null
        }
    }

    suspend fun incrementCartItemQuantity(cartItemId: Int) {
        _appDatabase.cartDao().incrementQuantity(cartItemId)
    }

    suspend fun decrementCartItemQuantity(cartItemId: Int) {
        _appDatabase.cartDao().decrementQuantity(cartItemId)
    }


    suspend fun deleteFromCart(cartItemId: Int) {
        try {
            _appDatabase.cartDao().deleteFromCart(cartItemId)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error deleting from cart: ${e.message}")
            throw e
        }
    }


    suspend fun clearCart() {
        try {
            _appDatabase.cartDao().clearCart()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error clearing cart: ${e.message}")
        }
    }
}