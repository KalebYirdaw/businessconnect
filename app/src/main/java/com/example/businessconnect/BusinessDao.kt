package com.example.businessconnect

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BusinessDao {

    @Insert
    suspend fun insertBusiness(
        business: BusinessEntity
    ): Long

    @Update
    suspend fun updateBusiness(
        business: BusinessEntity
    )

    @Delete
    suspend fun deleteBusiness(
        business: BusinessEntity
    )

    @Query(
        """
        SELECT * FROM businesses
        ORDER BY id DESC
        """
    )
    fun getAllBusinesses(): Flow<List<BusinessEntity>>

    @Query(
        """
        SELECT * FROM businesses
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun getBusinessById(
        id: Int
    ): BusinessEntity?
}