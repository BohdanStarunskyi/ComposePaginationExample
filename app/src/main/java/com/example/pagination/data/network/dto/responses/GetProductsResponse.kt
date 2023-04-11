package com.example.pagination.data.network.dto.responses

data class GetProductsResponse(
    val limit: Int? = null,
    val products: List<Product>? = null,
    val skip: Int? = null,
    val total: Int? = null
)