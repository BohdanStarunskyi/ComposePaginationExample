package com.example.pagination.presentation.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pagination.common.tests.getFakeProducts


@Composable
fun DetailsScreen(modifier: Modifier, id: Int) {
    val viewModel: DetailsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getProductDetails(id)
    }
    val state = viewModel.productState.value
    DetailsScreenContent(modifier = modifier, state = state)
}

@Composable
fun DetailsScreenContent(
    modifier: Modifier,
    state: SingleProductState
) {
    val product = state.product
    Column(modifier) {
        AsyncImage(
            model = product?.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
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
                text = product?.title ?: "",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Price: ${product?.price}",
                color = MaterialTheme.colorScheme.onBackground,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Text(
            text = product?.description ?: "",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 20.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    DetailsScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        state = SingleProductState(product = getFakeProducts(1).first())
    )
}