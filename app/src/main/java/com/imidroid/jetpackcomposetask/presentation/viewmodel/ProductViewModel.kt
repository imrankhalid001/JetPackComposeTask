package com.imidroid.jetpackcomposetask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imidroid.jetpackcomposetask.domain.model.Product
import com.imidroid.jetpackcomposetask.domain.repository.ProductRepository
import com.imidroid.jetpackcomposetask.presentation.screen.productstate.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel  @Inject constructor(
    private val repository: ProductRepository
) : ViewModel(){


    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState : StateFlow<UiState> = _uiState.asStateFlow()


    private val _message = MutableStateFlow<String?>("")
    val message : StateFlow<String?> = _message.asStateFlow()

    private val _localProducts = mutableListOf<Product>()

    private var productId = 1000


    init {
        loadProducts()
    }


    fun loadProducts(){
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val products = repository.getAllProducts()
                _localProducts.clear()
                _localProducts.addAll(products)
                _uiState.value = UiState.Success(_localProducts.toList())
                _message.value = "Loaded ${products.size} products (GET)"

            }catch (e : Exception){
                _uiState.value = UiState.Error(e.message ?: "Unknown error")
                _message.value = "Error loading products (GET) ${e.message}"
            }
        }

    }


    fun createProduct(product: Product){
        viewModelScope.launch {

            try {
                repository.createProduct(product)
                val localProduct = product.copy(id = productId++)
                _localProducts.add(localProduct)
                _uiState.value = UiState.Success(_localProducts.toList())
                _message.value = "Created product with id: ${localProduct.id} (POST)"

            }
            catch (e : Exception)
            {
                _message.value = "Error creating product (POST) ${e.message}"
            }
        }
    }



    fun updateProduct(id : Int, product: Product){

    try {

        viewModelScope.launch {
            repository.updateProduct(id, product)
            val index = _localProducts.indexOfFirst { it.id == id }
            if (index != -1) {
                _localProducts[index] = product.copy(id = id)
                _uiState.value = UiState.Success(_localProducts.toList())
                _message.value = "Updated product with id: $id (PUT)"
            } else {
                _message.value = "Product with id: $id not found (PUT)"
            }
        }
    }
        catch (e : Exception)
        {
            _message.value = "Error updating product (PUT) ${e.message}"
        }
    }


    fun patchProduct(id : Int, title: String){

        viewModelScope.launch {

            try {
                val updates = mapOf("title" to title)
                repository.patchProduct(id, updates)
                val index = _localProducts.indexOfFirst { it.id == id }

                if (index != -1) {
                    val updatedProduct = _localProducts[index].copy(title = title)
                    _localProducts[index] = updatedProduct
                    _uiState.value = UiState.Success(_localProducts.toList())
                    _message.value = "Patched product with id: $id (PATCH)"
                } else {
                    _message.value = "Product with id: $id not found (PATCH)"
                }
            }
            catch (e : Exception)
            {
                _message.value = "Error patching product (PATCH) ${e.message}"
            }
        }
    }


    fun deleteProduct(id : Int){
        viewModelScope.launch {
            try {
                repository.deletedProduct(id)
                _localProducts.removeAll { it.id == id }
                _uiState.value = UiState.Success(_localProducts.toList())
                _message.value = "Deleted successfully product with id: $id (DELETE)"

            }
            catch (e : Exception)
            {
                _message.value = "Error deleting product (DELETE) ${e.message}"

            }
        }

    }


    fun clearMessage(){
        _message.value = ""
    }











}





