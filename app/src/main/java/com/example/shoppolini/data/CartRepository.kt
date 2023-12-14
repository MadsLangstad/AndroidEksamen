package com.example.shoppolini.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

object CartRepository {

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

    // Add a product to the cart
    suspend fun addToCart(cartItem: Cart) {
        try {
            _appDatabase.cartDao().addToCart(cartItem)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error adding to cart: ${e.message}")
        }
    }

    // Get all the cart items
    fun getCartItems(): Flow<List<Cart>> {
        return try {
            _appDatabase.cartDao().getCartItems()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error getting cart items: ${e.message}")
            throw e
        }
    }

    // Delete a product from the cart
    suspend fun deleteFromCart(cartItemId: Int) {
        try {
            _appDatabase.cartDao().deleteFromCart(cartItemId)
        } catch (e: Exception) {
            Log.e("CartRepository", "Error deleting from cart: ${e.message}")
            throw e
        }
    }

    // Clear the cart
    suspend fun clearCart() {
        try {
            _appDatabase.cartDao().clearCart()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error clearing cart: ${e.message}")
        }
    }
}
