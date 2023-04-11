package com.example.pagination.data.network

import com.example.pagination.data.network.dto.responses.GetProductsResponse
import com.example.pagination.data.network.dto.responses.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): GetProductsResponse

    @GET("products/{product_id}")
    suspend fun getProductDetails(
        @Path("product_id") id: Int
    ): Product
}