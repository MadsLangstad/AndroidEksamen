package com.example.shoppolini.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.shoppolini.data.room.AppDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductRepository {

    private val _httpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

    private val _retrofit =
        Retrofit.Builder()
            .client(_httpClient)
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    private val _apiService =
        _retrofit.create(FakeStoreApi::class.java)

    private lateinit var _appDatabase: AppDatabase

    fun initiateAppDatabase(context: Context) {
        _appDatabase = Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            name = "appDatabase"
        ).fallbackToDestructiveMigration()
            .build();
    }

    suspend fun getProducts(): List<Product> {

        /**
        var list: List<Product> = listOf(Product(1, "Hårføner", 123.0, "Føn håret ditt", "Hårpleie", ""))
        return list
         **/
        try {
            val response = _apiService.getProducts()

            if (response.isSuccessful) {
                val product = response.body() ?: emptyList()
                _appDatabase.productDao().insertProducts(product)

                return _appDatabase.productDao().getProducts()
            } else {
                throw Exception("Failed to fetch astronauts")
            }
        } catch (e: Exception) {
            Log.d("ProductRepository", "Failed to fetch products, ExceptionMessage: ${e.message}")
            return _appDatabase.productDao().getProducts()
        }

    }


    suspend fun getProductById(productId: Int): Product {
        return _appDatabase.productDao().getProductById(productId)
    }
}
