package com.imidroid.jetpackcomposetask.presentation.screen.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.imidroid.jetpackcomposetask.domain.model.Product
import com.imidroid.jetpackcomposetask.presentation.screen.product.component.ProductDetailForm
import com.imidroid.jetpackcomposetask.presentation.screen.product.component.ProductList
import com.imidroid.jetpackcomposetask.presentation.screen.productstate.UiState
import com.imidroid.jetpackcomposetask.presentation.viewmodel.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductManager(viewModel: ProductViewModel) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val message by viewModel.message.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    var showEditProduct by remember { mutableStateOf(false) }
    var showAddProduct by remember { mutableStateOf(false) }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(message) {
        if (message?.isNotEmpty() == true) {
            snackbarHostState.showSnackbar(message!!)
            viewModel.clearMessage()
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Product Manager") },
                actions = {
                    IconButton(onClick = { viewModel.loadProducts() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription ="Refresh List of Products")
                    }
                    IconButton(onClick = { showAddProduct = true}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription ="Add a new Product to List")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    ProductList(
                        product = state.products,
                        onEdit = { product ->
                            selectedProduct = product
                            showEditProduct = true
                        },
                        onDelete = {
                            viewModel.deleteProduct(it.id)
                        },
                        onPatch = {
                            viewModel.patchProduct(it.id, "${it.title}(PATCH)")
                        }
                    )
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error : ${state.message}")
                    }
                }
            }
        }
    }

    if(showAddProduct) {
        ProductDetailForm(
            title = "Add Product",
            product = null,
            onDismiss = {
                showAddProduct = false
            },
            onSave = { product ->
                viewModel.createProducts(product)
                showAddProduct = false
            }
        )
    }

    if (showEditProduct) {
        ProductDetailForm(
            title = "Edit Product",
            product = selectedProduct,
            onDismiss = {
                showEditProduct = false
                selectedProduct = null
            },
            onSave = { product ->
                selectedProduct?.let {
                    viewModel.updateProduct(it.id, product)
                }
                showEditProduct = false
                selectedProduct = null
            }
        )
    }
}
