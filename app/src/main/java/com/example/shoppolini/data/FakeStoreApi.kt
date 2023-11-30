package com.example.shoppolini.data

import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {

    @GET("products/?limit=100")
    suspend fun getProducts(): Response<List<Product>>

    @GET("products/{productId}")
    suspend fun getProductById(
        @Path("productId") productId: Int
    ): Response<Product>
}