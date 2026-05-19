package com.imidroid.jetpackcomposetask.presentation.screen.productstate

import com.imidroid.jetpackcomposetask.domain.model.Product

sealed class UiState {

    object Loading : UiState()
    data class Success(val products : List<Product>) : UiState()
    data class Error(val message : String) : UiState()



}