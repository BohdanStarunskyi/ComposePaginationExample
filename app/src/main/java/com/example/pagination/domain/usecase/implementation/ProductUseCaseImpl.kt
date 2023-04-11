package com.example.pagination.domain.usecase.implementation

import com.example.pagination.data.repository.ProductRepositoryImpl
import com.example.pagination.domain.entities.ProductEntity
import com.example.pagination.domain.usecase.interfaces.ProductUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductUseCaseImpl @Inject constructor(
    private val productRepository: ProductRepositoryImpl
) : ProductUseCase {

    override suspend fun getProducts(limit: Int, offset: Int): List<ProductEntity>? =
        productRepository.getProducts(limit, offset)

    override suspend fun getProductDetails(id: Int): ProductEntity =
        productRepository.getProductDetails(id)

}