package com.example.businessconnect

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductCatalogueDao {

    @Insert
    suspend fun insertProduct(
        product: ProductCatalogueEntity
    )

    @Update
    suspend fun updateProduct(
        product: ProductCatalogueEntity
    )

    @Delete
    suspend fun deleteProduct(
        product: ProductCatalogueEntity
    )

    @Query(
        """
        SELECT * FROM product_catalogue
        ORDER BY name ASC
        """
    )
    fun getAllProducts():
            Flow<List<ProductCatalogueEntity>>

    @Query(
        """
        SELECT * FROM product_catalogue
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun getProductById(
        id: Int
    ): ProductCatalogueEntity?

    @Query(
        """
        SELECT COUNT(*) FROM product_catalogue
        WHERE name = :name
        """
    )
    suspend fun countProductByName(
        name: String
    ): Int

    @Query(
        """
        SELECT COUNT(*) FROM product_catalogue
        """
    )
    suspend fun getProductCount(): Int
}