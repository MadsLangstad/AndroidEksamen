package com.example.shoppolini.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.shoppolini.data.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM Products")
    fun getAstronauts(): Flow<List<Product>>

    @Query("SELECT * FROM Products WHERE :productId = id")
    fun getProductById(productId: Int): Flow<Product?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProduct(astronaut: Product)
}