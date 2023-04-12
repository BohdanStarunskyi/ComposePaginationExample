package com.example.pagination.presentation.ui.main.states

import com.example.pagination.domain.entities.ProductEntity

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<ProductEntity> = listOf(),
    val error: Throwable? = null
)
