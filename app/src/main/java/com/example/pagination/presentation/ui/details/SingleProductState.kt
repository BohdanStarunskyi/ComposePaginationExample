package com.example.pagination.presentation.ui.details

import com.example.pagination.domain.entities.ProductEntity

data class SingleProductState(
    val isLoading: Boolean = false,
    val product: ProductEntity? = null,
    val error: Throwable? = null
)
