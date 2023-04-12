package com.example.pagination.presentation.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagination.common.constants.Constants
import com.example.pagination.domain.usecase.implementation.ProductUseCaseImpl
import com.example.pagination.presentation.ui.main.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ProductUseCaseImpl
) : ViewModel() {
    private val _productsState = mutableStateOf(ProductsState())
    val productsState: State<ProductsState> = _productsState
    var isEndOfList = false
    fun getProducts(offset: Int, limit: Int) {
        viewModelScope.launch {
            runCatching {
                _productsState.value = _productsState.value.copy(isLoading = true)
                useCase.getProducts(limit, offset)
            }.onSuccess { products ->
                if ((products?.size ?: 0) < Constants.API_LIST_LIMIT)
                    isEndOfList = true
                val list =
                    _productsState.value.copy().products.plus(products ?: listOf())
                        .distinctBy { it.id }
                _productsState.value =
                    _productsState.value.copy(isLoading = false, products = list)
            }.onFailure {
                _productsState.value = _productsState.value.copy(isLoading = false, error = it)
            }
        }
    }
}