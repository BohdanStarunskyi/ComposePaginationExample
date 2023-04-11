package com.example.pagination.data.repository

import com.example.pagination.common.annotations.IoDispatcher
import com.example.pagination.data.network.API
import com.example.pagination.domain.entities.ProductEntity
import com.example.pagination.domain.mappers.toProductEntity
import com.example.pagination.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val api: API,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ProductRepository {
    override suspend fun getProducts(limit: Int, offset: Int): List<ProductEntity>? =
        withContext(ioDispatcher) {
            api.getProducts(limit, offset).products?.map { it.toProductEntity() }
        }

    override suspend fun getProductDetails(id: Int): ProductEntity = withContext(ioDispatcher) {
        api.getProductDetails(id).toProductEntity()
    }
}