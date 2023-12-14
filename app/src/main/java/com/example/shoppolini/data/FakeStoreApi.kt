package com.example.shoppolini.data

import retrofit2.Response
import retrofit2.http.GET


interface FakeStoreApi {

    /*
        This makes the app crash on a real phone, but not on the emulator
        I tried to get help on this in school, but there was no student assistant there for the rest of the semester

        Something with the Response<List<Product>> is wrong, but I don't know what. But the app works fine on emulator :)
                                         ⬇                                                                                */
    @GET("products/?limit=100")
    suspend fun getProducts(): Response<List<Product>>
}