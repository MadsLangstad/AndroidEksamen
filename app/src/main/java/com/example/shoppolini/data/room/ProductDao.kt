package com.example.shoppolini.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppolini.data.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM Products WHERE :productId = id")
    suspend fun getProductById(productId: Int): Product

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Query("SELECT * FROM Products WHERE title LIKE :searchQuery")
    suspend fun searchProducts(searchQuery: String): List<Product>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(product: Product)
}