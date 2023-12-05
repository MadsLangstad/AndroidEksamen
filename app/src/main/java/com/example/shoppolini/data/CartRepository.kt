package com.example.shoppolini.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase

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
    suspend fun getCartItems(): List<Cart> {
        return try {
            _appDatabase.cartDao().getCartItems()
        } catch (e: Exception) {
            Log.e("CartRepository", "Error fetching cart items: ${e.message}")
            emptyList()
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
