package com.example.businessconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    fun getProductsForBusiness(
        businessId: Int
    ): Flow<List<ProductEntity>> {

        return repository.getProductsForBusiness(
            businessId
        )
    }

    fun saveProduct(
        product: ProductEntity
    ) {

        viewModelScope.launch {

            repository.insertProduct(
                product
            )
        }
    }

    fun updateProduct(
        product: ProductEntity
    ) {

        viewModelScope.launch {

            repository.updateProduct(
                product
            )
        }
    }

    fun deleteProduct(
        product: ProductEntity
    ) {

        viewModelScope.launch {

            repository.deleteProduct(
                product
            )
        }
    }

    fun deleteProductsForBusiness(
        businessId: Int
    ) {

        viewModelScope.launch {

            repository.deleteProductsForBusiness(
                businessId
            )
        }
    }

    suspend fun getProductsForBusinessOnce(
        businessId: Int
    ): List<ProductEntity> {

        return repository.getProductsForBusinessOnce(
            businessId
        )
    }
}

class ProductViewModelFactory(
    private val repository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                ProductViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return ProductViewModel(
                repository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}