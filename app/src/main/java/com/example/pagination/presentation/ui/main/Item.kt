@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pagination.presentation.ui.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.pagination.common.tests.getFakeProducts
import com.example.pagination.domain.entities.ProductEntity

@Composable
fun Item(
    modifier: Modifier,
    productEntity: ProductEntity,
    onProductClick: () -> Unit
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onProductClick,
        colors = CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primary),
        border = BorderStroke(0.dp, color = Color.Transparent),
        elevation = CardDefaults.outlinedCardElevation(defaultElevation = 10.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        AsyncImage(
            model = productEntity.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .height(190.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = productEntity.title ?: "",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Price: ${productEntity.price}",
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Text(
            text = "ID: ${productEntity.id}",
            color = MaterialTheme.colorScheme.onPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    Item(
        modifier = Modifier.fillMaxWidth(),
        productEntity = getFakeProducts(1).first(),
        onProductClick = {}
    )
}