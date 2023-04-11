package com.example.pagination.presentation.ui.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pagination.domain.usecase.implementation.ProductUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCase: ProductUseCaseImpl
) : ViewModel() {
    private val _productState = mutableStateOf(SingleProductState())
    val productState: State<SingleProductState> = _productState

    fun getProductDetails(id: Int) {
        viewModelScope.launch {
            runCatching {
                _productState.value = SingleProductState(isLoading = true)
                useCase.getProductDetails(id)
            }.onSuccess {
                _productState.value = SingleProductState(product = it)
            }.onFailure {
                _productState.value = SingleProductState(error = it)
            }
        }
    }
}