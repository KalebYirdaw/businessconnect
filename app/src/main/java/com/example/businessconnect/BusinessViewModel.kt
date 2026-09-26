package com.example.businessconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BusinessViewModel(
    private val businessRepository: BusinessRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    val businesses: Flow<List<BusinessEntity>> =
        businessRepository.getAllBusinesses()

    fun saveBusiness(
        business: BusinessEntity,
        products: List<ProductAssessment>
    ) {

        viewModelScope.launch {

            val businessId =
                businessRepository
                    .insertBusiness(
                        business
                    )
                    .toInt()

            products.forEach { product ->

                productRepository.insertProduct(
                    ProductEntity(
                        businessId =
                            businessId,

                        productName =
                            product.productName,

                        productCategory =
                            product.productCategory,

                        availability =
                            product.availability,

                        quantity =
                            product.quantity,

                        price =
                            product.price,

                        notes =
                            product.notes
                    )
                )
            }
        }
    }

    fun updateBusiness(
        business: BusinessEntity,
        products: List<ProductEntity>
    ) {

        viewModelScope.launch {

            // Update the main business record.
            businessRepository.updateBusiness(
                business
            )

            // Remove the old product records.
            productRepository.deleteProductsForBusiness(
                business.id
            )

            // Insert the updated products.
            products.forEach { product ->

                productRepository.insertProduct(
                    product.copy(
                        id = 0,
                        businessId = business.id
                    )
                )
            }
        }
    }

    fun deleteBusiness(
        business: BusinessEntity
    ) {

        viewModelScope.launch {

            businessRepository.deleteBusiness(
                business
            )
        }
    }

    suspend fun getBusinessById(
        id: Int
    ): BusinessEntity? {

        return businessRepository.getBusinessById(
            id
        )
    }
}

class BusinessViewModelFactory(
    private val businessRepository: BusinessRepository,
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (
            modelClass.isAssignableFrom(
                BusinessViewModel::class.java
            )
        ) {

            @Suppress("UNCHECKED_CAST")

            return BusinessViewModel(
                businessRepository,
                productRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}