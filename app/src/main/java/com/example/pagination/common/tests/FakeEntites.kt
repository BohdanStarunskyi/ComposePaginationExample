package com.example.pagination.common.tests

import com.example.pagination.domain.entities.ProductEntity

fun getFakeProducts(count: Int): List<ProductEntity> {
    val list = arrayListOf<ProductEntity>()
    for (i in 0 until count) {
        list.add(
            ProductEntity(
                id = 1,
                imageUrl = "https://i.imgur.com/67grKmw.jpeg",
                title = "Kitty :D",
                price = 1_000_000,
                description = "a beaurifyl kitty"
            )
        )
    }
    return list
}