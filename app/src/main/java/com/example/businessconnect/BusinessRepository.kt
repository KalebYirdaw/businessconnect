package com.example.businessconnect

import kotlinx.coroutines.flow.Flow

class BusinessRepository(
    private val businessDao: BusinessDao
) {

    suspend fun insertBusiness(
        business: BusinessEntity
    ): Long {

        return businessDao.insertBusiness(
            business
        )
    }

    suspend fun updateBusiness(
        business: BusinessEntity
    ) {

        businessDao.updateBusiness(
            business
        )
    }

    suspend fun deleteBusiness(
        business: BusinessEntity
    ) {

        businessDao.deleteBusiness(
            business
        )
    }

    fun getAllBusinesses():
            Flow<List<BusinessEntity>> {

        return businessDao.getAllBusinesses()
    }

    suspend fun getBusinessById(
        id: Int
    ): BusinessEntity? {

        return businessDao.getBusinessById(
            id
        )
    }
}