package com.example.pagination.domain.repository

import com.example.pagination.domain.entities.ProductEntity

interface ProductRepository {
    suspend fun getProducts(limit: Int, offset: Int): List<ProductEntity>?
    suspend fun getProductDetails(id: Int): ProductEntity
}