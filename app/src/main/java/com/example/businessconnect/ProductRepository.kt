package com.example.businessconnect

import kotlinx.coroutines.flow.Flow

class ProductRepository(
    private val productDao: ProductDao
) {

    suspend fun insertProduct(
        product: ProductEntity
    ) {
        productDao.insertProduct(product)
    }

    suspend fun updateProduct(
        product: ProductEntity
    ) {
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(
        product: ProductEntity
    ) {
        productDao.deleteProduct(product)
    }

    fun getProductsForBusiness(
        businessId: Int
    ): Flow<List<ProductEntity>> {
        return productDao.getProductsForBusiness(
            businessId
        )
    }

    suspend fun getProductsForBusinessOnce(
        businessId: Int
    ): List<ProductEntity> {
        return productDao.getProductsForBusinessOnce(
            businessId
        )
    }

    suspend fun deleteProductsForBusiness(
        businessId: Int
    ) {
        productDao.deleteProductsForBusiness(
            businessId
        )
    }
}