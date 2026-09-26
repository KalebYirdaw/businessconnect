package com.example.businessconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class BusinessViewModel(
    private val repository: BusinessRepository
) : ViewModel() {

    val businesses: Flow<List<BusinessEntity>> =
        repository.getAllBusinesses()

    fun saveBusiness(business: BusinessEntity) {

        viewModelScope.launch {
            repository.insertBusiness(business)
        }
    }

    fun updateBusiness(business: BusinessEntity) {

        viewModelScope.launch {
            repository.updateBusiness(business)
        }
    }

    fun deleteBusiness(business: BusinessEntity) {

        viewModelScope.launch {
            repository.deleteBusiness(business)
        }
    }

    suspend fun getBusinessById(id: Int): BusinessEntity? {
        return repository.getBusinessById(id)
    }
}

class BusinessViewModelFactory(
    private val repository: BusinessRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        if (modelClass.isAssignableFrom(BusinessViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            return BusinessViewModel(repository) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class"
        )
    }
}