package com.example.pagination.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pagination.common.constants.Routes
import com.example.pagination.common.tests.getFakeProducts
import com.example.pagination.domain.entities.ProductEntity
import com.example.pagination.presentation.ui.main.states.OffsetState
import com.example.pagination.presentation.ui.main.states.ProductsState

@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavController,
    listState: LazyListState,
    offsetState: MutableState<OffsetState>
) {
    val viewModel: MainViewModel = hiltViewModel()
    val state = viewModel.productsState.value
    LaunchedEffect(Unit) {
        viewModel.getProducts(offsetState.value.bottomOffset, 20, false)
    }
    MainScreenContent(
        modifier = modifier,
        onItemClick = {
            runCatching {
                navController.navigate("${Routes.DETAILS.route}/$it")
            }
        },
        state = state,
        onLoadMore = {
            if (!state.isLoading)
                viewModel.getProducts(state.products.size, 10, true)
        },
        listState = listState
    )
}

private fun isEndOfList(listState: LazyListState, apartments: List<ProductEntity>) =
    listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == apartments.lastIndex

@Composable
fun MainScreenContent(
    modifier: Modifier,
    onItemClick: (id: Int) -> Unit,
    state: ProductsState,
    listState: LazyListState,
    onLoadMore: () -> Unit
) {
    if (isEndOfList(listState, state.products) && state.products.isNotEmpty()) {
        onLoadMore()
    }
    LazyColumn(
        modifier = modifier,
        state = listState
    ) {
        items(state.products) { product ->
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
        state = ProductsState(products = getFakeProducts(10)),
        onLoadMore = {},
        listState = rememberLazyListState()
    )
}