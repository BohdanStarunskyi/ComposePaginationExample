package com.example.pagination.domain.mappers

import com.example.pagination.data.network.dto.responses.Product
import com.example.pagination.domain.entities.ProductEntity

fun Product.toProductEntity() = ProductEntity(
    id = this.id,
    imageUrl = this.thumbnail,
    title = this.title,
    price = this.price,
    description = this.description
)