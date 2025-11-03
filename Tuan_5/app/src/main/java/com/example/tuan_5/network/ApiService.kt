package com.example.apidemo.network

import com.example.apidemo.model.Product
import retrofit2.http.GET

interface ApiService {
    // GET https://mock.apidog.com/m1/890655-872447-default/v2/product
    @GET("v2/product")
    suspend fun getProducts(): List<Product>
}
