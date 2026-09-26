package com.example.businessconnect

import kotlinx.coroutines.flow.Flow

class ProductCatalogueRepository(
    private val dao: ProductCatalogueDao
) {

    suspend fun insertProduct(
        product: ProductCatalogueEntity
    ) {

        dao.insertProduct(
            product
        )
    }

    suspend fun updateProduct(
        product: ProductCatalogueEntity
    ) {

        dao.updateProduct(
            product
        )
    }

    suspend fun deleteProduct(
        product: ProductCatalogueEntity
    ) {

        dao.deleteProduct(
            product
        )
    }

    fun getAllProducts():
            Flow<List<ProductCatalogueEntity>> {

        return dao.getAllProducts()
    }

    suspend fun getProductById(
        id: Int
    ): ProductCatalogueEntity? {

        return dao.getProductById(
            id
        )
    }

    suspend fun countProductByName(
        name: String
    ): Int {

        return dao.countProductByName(
            name
        )
    }

    suspend fun getProductCount(): Int {

        return dao.getProductCount()
    }
}