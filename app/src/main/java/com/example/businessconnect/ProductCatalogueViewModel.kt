package com.example.businessconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductCatalogueViewModel(
    private val repository:
    ProductCatalogueRepository
) : ViewModel() {

    val products:
            Flow<List<ProductCatalogueEntity>> =
        repository.getAllProducts()

    fun seedDefaultProducts() {

        viewModelScope.launch {

            val productCount =
                repository.getProductCount()

            if (productCount > 0) {
                return@launch
            }

            ProductCatalogue.products.forEach {
                    product ->

                repository.insertProduct(
                    ProductCatalogueEntity(
                        name =
                            product.name,

                        category =
                            product.category
                    )
                )
            }
        }
    }

    fun addProduct(
        name: String,
        category: String
    ) {

        viewModelScope.launch {

            val cleanName =
                name.trim()

            val cleanCategory =
                category.trim()

            if (
                cleanName.isBlank() ||
                cleanCategory.isBlank()
            ) {
                return@launch
            }

            val existingCount =
                repository.countProductByName(
                    cleanName
                )

            if (existingCount > 0) {
                return@launch
            }

            repository.insertProduct(
                ProductCatalogueEntity(
                    name =
                        cleanName,

                    category =
                        cleanCategory
                )
            )
        }
    }

    fun updateProduct(
        product:
        ProductCatalogueEntity
    ) {

        viewModelScope.launch {

            repository.updateProduct(
                product
            )
        }
    }

    fun deleteProduct(
        product:
        ProductCatalogueEntity
    ) {

        viewModelScope.launch {

            repository.deleteProduct(
                product
            )
        }
    }
}

class ProductCatalogueViewModelFactory(
    private val repository:
    ProductCatalogueRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ProductCatalogueViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return ProductCatalogueViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}