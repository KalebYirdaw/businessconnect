package com.example.businessconnect

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(
        product: ProductEntity
    )

    @Update
    suspend fun updateProduct(
        product: ProductEntity
    )

    @Delete
    suspend fun deleteProduct(
        product: ProductEntity
    )

    @Query(
        """
        SELECT * FROM products
        WHERE businessId = :businessId
        ORDER BY id ASC
        """
    )
    fun getProductsForBusiness(
        businessId: Int
    ): Flow<List<ProductEntity>>

    @Query(
        """
        SELECT * FROM products
        WHERE businessId = :businessId
        ORDER BY id ASC
        """
    )
    suspend fun getProductsForBusinessOnce(
        businessId: Int
    ): List<ProductEntity>

    @Query(
        """
        DELETE FROM products
        WHERE businessId = :businessId
        """
    )
    suspend fun deleteProductsForBusiness(
        businessId: Int
    )
}