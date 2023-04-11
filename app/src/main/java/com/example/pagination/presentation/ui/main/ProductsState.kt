package com.example.pagination.presentation.ui.main

import com.example.pagination.domain.entities.ProductEntity

data class ProductsState(
    val isLoading: Boolean = false,
    val products: List<ProductEntity>? = null,
    val error: Throwable? = null
)
