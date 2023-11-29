package com.example.shoppolini.data

object ProductRepository {
    suspend fun getProducts(): List<Product> {

        val response = RetrofitInstance.apiService.getProducts()

        if (response.isSuccessful) {

            return response.body() ?: emptyList()

        } else {

            throw Exception("Error fetching products")

        }
    }

    suspend fun getProductById(productId: Int): Product {

            val response = RetrofitInstance.apiService.getProductById(productId)

            if (response.isSuccessful) {

                return response.body()!!

            } else {

                throw Exception("Error fetching product details")

            }
    }
}

