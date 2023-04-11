package com.example.pagination.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pagination.common.constants.Routes
import com.example.pagination.common.tests.getFakeProducts

@Composable
fun MainScreen(modifier: Modifier, navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.getProducts(0, 100)
    }
    val state = viewModel.productsState.value
    MainScreenContent(
        modifier = modifier,
        onItemClick = {
            runCatching {
                navController.navigate("${Routes.DETAILS.route}/$it")
            }
        },
        state = state
    )
}

@Composable
fun MainScreenContent(
    modifier: Modifier,
    onItemClick: (id: Int) -> Unit,
    state: ProductsState
) {
    LazyColumn(modifier) {
        items(state.products ?: listOf()) { product ->
            Item(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                productEntity = product,
                onProductClick = { product.id?.let(onItemClick) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        onItemClick = {},
        state = ProductsState(products = getFakeProducts(10))
    )
}