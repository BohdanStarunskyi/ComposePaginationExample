package com.example.pagination.presentation.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagination.domain.usecase.implementation.ProductUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: ProductUseCaseImpl
) : ViewModel() {
    private val _productsState = mutableStateOf(ProductsState())
    val productsState: State<ProductsState> = _productsState

    fun getProducts(offset: Int, limit: Int) {
        viewModelScope.launch {
            runCatching {
                _productsState.value = ProductsState(isLoading = true)
                useCase.getProducts(limit, offset)
            }.onSuccess {
                _productsState.value = ProductsState(products = it)
            }.onFailure {
                _productsState.value = ProductsState(error = it)
            }
        }
    }
}