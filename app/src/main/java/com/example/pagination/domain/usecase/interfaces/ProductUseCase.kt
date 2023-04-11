package com.example.pagination.domain.usecase.interfaces

import com.example.pagination.domain.entities.ProductEntity

interface ProductUseCase {
    suspend fun getProducts(limit: Int, offset: Int): List<ProductEntity>?
    suspend fun getProductDetails(id: Int): ProductEntity
}