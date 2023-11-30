package com.example.shoppolini.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    @GET("products")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Response<Product?>
}